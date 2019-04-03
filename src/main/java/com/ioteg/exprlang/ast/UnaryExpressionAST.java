package com.ioteg.exprlang.ast;

import java.util.Map;

import com.ioteg.exprlang.Token;

/**
 * <p>UnaryExpressionAST class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class UnaryExpressionAST implements ExpressionAST {
	private ExpressionAST expression;
	private Token unaryOp;
	
	/**
	 * <p>Constructor for UnaryExpressionAST.</p>
	 *
	 * @param expr a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 * @param unaryOp a {@link com.ioteg.exprlang.Token} object.
	 */
	public UnaryExpressionAST(ExpressionAST expr, Token unaryOp) {
		super();
		this.expression = expr;
		this.unaryOp = unaryOp;
	}

	/** {@inheritDoc} */
	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;

		if(unaryOp == Token.TOK_OP_SUB)
			result = -expression.evaluate(symbols);
		
		return result;
	}

	/**
	 * <p>Getter for the field <code>expression</code>.</p>
	 *
	 * @return a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 */
	public ExpressionAST getExpression() {
		return expression;
	}

	/**
	 * <p>Getter for the field <code>unaryOp</code>.</p>
	 *
	 * @return a {@link com.ioteg.exprlang.Token} object.
	 */
	public Token getUnaryOp() {
		return unaryOp;
	}
	
}
