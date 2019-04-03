package com.ioteg.exprlang.ast;

import java.util.Map;

/**
 * <p>VariableExpressionAST class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class VariableExpressionAST implements ExpressionAST{
	private String name;
	
	/**
	 * <p>Constructor for VariableExpressionAST.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public VariableExpressionAST(String name) {
		super();
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public Double evaluate(Map<String, Double> symbols) {
		return symbols.get(name);
	}
}
