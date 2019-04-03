package com.ioteg.exprlang.ast;

import java.util.Map;

import com.ioteg.exprlang.Token;

/**
 * <p>BinaryExpressionAST class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class BinaryExpressionAST implements ExpressionAST {
	private Token operator;
	private ExpressionAST lhs;
	private ExpressionAST rhs;

	/**
	 * <p>Constructor for BinaryExpressionAST.</p>
	 *
	 * @param operator a {@link com.ioteg.exprlang.Token} object.
	 * @param lhs a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 * @param rhs a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 */
	public BinaryExpressionAST(Token operator, ExpressionAST lhs, ExpressionAST rhs) {
		super();
		this.operator = operator;
		this.lhs = lhs;
		this.rhs = rhs;
	}

	/**
	 * <p>Getter for the field <code>operator</code>.</p>
	 *
	 * @return a {@link com.ioteg.exprlang.Token} object.
	 */
	public Token getOperator() {
		return operator;
	}

	/**
	 * <p>Getter for the field <code>lhs</code>.</p>
	 *
	 * @return a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 */
	public ExpressionAST getLhs() {
		return lhs;
	}

	/**
	 * <p>Getter for the field <code>rhs</code>.</p>
	 *
	 * @return a {@link com.ioteg.exprlang.ast.ExpressionAST} object.
	 */
	public ExpressionAST getRhs() {
		return rhs;
	}

	/** {@inheritDoc} */
	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;

		switch (operator) {
		case TOK_OP_DIV:
			result = lhs.evaluate(symbols) / rhs.evaluate(symbols);
			break;
		case TOK_OP_MUL:
			result = lhs.evaluate(symbols) * rhs.evaluate(symbols);
			break;
		case TOK_OP_SUB:
			result = lhs.evaluate(symbols) - rhs.evaluate(symbols);
			break;
		case TOK_OP_SUM:
			result = lhs.evaluate(symbols) + rhs.evaluate(symbols);
			break;
		default:
			break;
		}
		
		return result;
	}
}
