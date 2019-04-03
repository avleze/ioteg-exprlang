package com.ioteg.exprlang;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ioteg.exprlang.ast.BinaryExpressionAST;
import com.ioteg.exprlang.ast.CallExpressionAST;
import com.ioteg.exprlang.ast.ExpressionAST;
import com.ioteg.exprlang.ast.NumberExpressionAST;
import com.ioteg.exprlang.ast.UnaryExpressionAST;
import com.ioteg.exprlang.ast.VariableExpressionAST;

/**
 * <p>ExprParser class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ExprParser {
	private ExprLexer lexer;
	/** Constant <code>tokenPrecedence</code> */
	protected static final Map<Token, Integer> tokenPrecedence;
	/** Constant <code>logger</code> */
	protected static Logger logger;
	
	static {
		logger = Logger.getRootLogger();
		tokenPrecedence = new EnumMap<>(Token.class);
		tokenPrecedence.put(Token.TOK_OP_SUM, 20);
		tokenPrecedence.put(Token.TOK_OP_SUB, 20);
		tokenPrecedence.put(Token.TOK_OP_MUL, 40);
		tokenPrecedence.put(Token.TOK_OP_DIV, 40);
	}
	
	public class ExprLangParsingException extends Exception {

		private static final long serialVersionUID = 1L;

		public ExprLangParsingException(String msg) {
			super(msg);
		}
	}

	/**
	 * <p>parse.</p>
	 *
	 * @param str a {@link java.lang.String} object.
	 * @return a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 */
	public ExpressionAST parse(String str) throws ExprLangParsingException  {
		this.lexer = new ExprLexer(str);
		lexer.getNextToken();
		return parseExpression();
	}

	private ExpressionAST parseExpression() throws ExprLangParsingException {
		ExpressionAST lhs = parsePrimary();

		if (lhs == null)
			return null;

		return parseBinaryExpressionRHS(0, lhs);
	}

	private ExpressionAST parseBinaryExpressionRHS(int expressionPrecedence, ExpressionAST lhs) throws ExprLangParsingException {
		while (true) {
			int tokPrecedence = getTokenPrecedence();

			if (tokPrecedence < expressionPrecedence)
				return lhs;

			Token binOp = lexer.getCurrentToken();
			lexer.getNextToken();
			ExpressionAST rhs = parsePrimary();
			if (rhs == null)
				return null;

			// If BinOp binds less tightly with RHS than the operator after RHS, let
			// the pending operator take RHS as its LHS.
			int nextPrecedence = getTokenPrecedence();
			if (tokPrecedence < nextPrecedence) {
				rhs = parseBinaryExpressionRHS(tokPrecedence + 1, rhs);
				if (rhs == null)
					return null;
			}

			// Merge LHS/RHS.
			lhs = new BinaryExpressionAST(binOp, lhs, rhs);
		}
	}

	private int getTokenPrecedence() {
		Integer precedence = tokenPrecedence.get(lexer.getCurrentToken());
		if (precedence == null)
			return -1;
		else
			return precedence;
	}

	private ExpressionAST parsePrimary() throws ExprLangParsingException  {
		ExpressionAST exp = null;

		switch (lexer.getCurrentToken()) {
		case TOK_DOLLAR:
			exp = parseVariableExpression();
			break;
		case TOK_OPEN_PAREN:
			exp = parseParenthesisExpression();
			break;
		case TOK_NUMBER:
			exp = parseNumberExpression();
			break;
		case TOK_ID:
			exp = parseCallFunctionExpression();
			break;
		case TOK_OP_SUB:
		case TOK_OP_SUM:
			exp = parseUnaryExpression();
			break;
		default:
			throw new ExprLangParsingException("Expected a expression.");
		}

		return exp;
	}

	private ExpressionAST parseUnaryExpression() throws ExprLangParsingException {
		Token operator = lexer.getCurrentToken();
		lexer.getNextToken();
		ExpressionAST primary = parsePrimary();
		if(primary == null)
			return null;
		
		if(operator == Token.TOK_OP_SUM)
			return primary;
		return new UnaryExpressionAST(primary, operator);
	}

	private ExpressionAST parseCallFunctionExpression() throws ExprLangParsingException {
		String idName = lexer.getCurrentMatch();

		if (lexer.getNextToken() != Token.TOK_OPEN_PAREN)
			throw new ExprLangParsingException("The function " + idName + " needs a list of zero or more arguments. Expected (.");
		lexer.getNextToken();
		List<ExpressionAST> args = new ArrayList<>();

		if (lexer.getCurrentToken() != Token.TOK_CLOSED_PAREN) {

			while (true) {
				ExpressionAST arg = parseExpression();
				if (arg != null)
					args.add(arg);
				else
					return null;

				if (lexer.getCurrentToken() == Token.TOK_CLOSED_PAREN)
					break;
				if (lexer.getCurrentToken() != Token.TOK_COMMA)
					throw new ExprLangParsingException("Unexpected input in the arguments list of the function " + idName + ".");

				lexer.getNextToken();
			}

		}

		lexer.getNextToken();
		return new CallExpressionAST(idName, args);
	}

	private ExpressionAST parseVariableExpression() {

		if (lexer.getNextToken() != Token.TOK_OPEN_PAREN) {
			logger.error("Expected (.");
			return null;
		}

		if (lexer.getNextToken() != Token.TOK_ID) {
			logger.error("Expected IDENTIFIER.");
			return null;
		}

		String idName = lexer.getCurrentMatch();

		if (lexer.getNextToken() != Token.TOK_CLOSED_PAREN)
			logger.error("Expected ).");
		else
			lexer.getNextToken();

		return new VariableExpressionAST(idName);
	}

	private ExpressionAST parseNumberExpression() {

		ExpressionAST exp = new NumberExpressionAST(Double.valueOf(lexer.getCurrentMatch()));
		lexer.getNextToken();
		return exp;
	}

	private ExpressionAST parseParenthesisExpression() throws ExprLangParsingException {
		ExpressionAST exp = null;
		lexer.getNextToken();
		exp = parseExpression();

		if (exp == null)
			return null;

		if (lexer.getCurrentToken() != Token.TOK_CLOSED_PAREN)
			logger.error("Expected ). But found " + lexer.getCurrentToken());
		lexer.getNextToken();
		return exp;
	}
}
