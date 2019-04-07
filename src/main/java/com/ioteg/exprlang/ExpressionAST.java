package com.ioteg.exprlang;

import java.util.Map;

/**
 * <p>ExpressionAST interface.</p>
 *
 * @author antonio
 * @version %I%, %G%
 */
public interface ExpressionAST {
	
	/**
	 * <p>evaluate.</p>
	 *
	 * @param symbols a {@link java.util.Map} with the value of the symbols to use for the evaluation.
	 * @return a {@link java.lang.Double} containing the result of the evaluation.
	 */
	public abstract Double evaluate(Map<String, Double> symbols);
}
