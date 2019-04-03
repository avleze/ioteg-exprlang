package com.ioteg.exprlang.ast;

import java.util.Map;

/**
 * <p>NumberExpressionAST class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class NumberExpressionAST implements ExpressionAST{
	private Double value;
	
	/**
	 * <p>Constructor for NumberExpressionAST.</p>
	 *
	 * @param value a {@link java.lang.Double} object.
	 */
	public NumberExpressionAST(Double value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.Double} object.
	 */
	public Double getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public Double evaluate(Map<String, Double> symbols) {
		return value;
	}
	
}
