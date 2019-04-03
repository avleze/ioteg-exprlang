package com.ioteg.exprlang.ast;

import java.util.Map;

/**
 * <p>ExpressionAST interface.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public interface ExpressionAST {
	
	/**
	 * <p>evaluate.</p>
	 *
	 * @param symbols a {@link java.util.Map} object.
	 * @return a {@link java.lang.Double} object.
	 */
	public abstract Double evaluate(Map<String, Double> symbols);
}
