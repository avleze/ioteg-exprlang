package com.ioteg.exprlang.ast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;


/**
 * <p>CallExpressionAST class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CallExpressionAST implements ExpressionAST{
	private String fnName;
	private List<ExpressionAST> args;
	
	/** Constant <code>r</code> */
	protected static Random r;
	/** Constant <code>logger</code> */
	protected static Logger logger;

	static {
		logger = Logger.getRootLogger();
	}
	
	/**
	 * <p>Constructor for CallExpressionAST.</p>
	 *
	 * @param fnName a {@link java.lang.String} object.
	 * @param args a {@link java.util.List} object.
	 */
	public CallExpressionAST(String fnName, List<ExpressionAST> args) {
		super();
		this.fnName = fnName;
		this.args = args;
	}

	/**
	 * <p>Getter for the field <code>fnName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFnName() {
		return fnName;
	}

	/**
	 * <p>Getter for the field <code>args</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ExpressionAST> getArgs() {
		return args;
	}

	/** {@inheritDoc} */
	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;
		Object resultObject = null;
		
		try {
			if(isUnaryFunction())
				resultObject = callUnaryFunction(symbols);
			else if(isBinaryFunction())
				resultObject = callBinaryFunction(symbols);
		} catch(Exception e) {
			logger.error(e);
		}
		
		if(resultObject instanceof Double)
			result = (Double) resultObject;
		else if(resultObject instanceof Long || resultObject instanceof Integer)
			result = Double.valueOf(resultObject.toString());
		else 
			result = null;
		
		return result; 
	}

	private boolean isBinaryFunction() {
		return args.size() == 2;
	}

	private boolean isUnaryFunction() {
		return args.size() == 1;
	}

	private Object callBinaryFunction(Map<String, Double> symbols)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object resultObject;
		Method fn = Math.class.getMethod(fnName, double.class, double.class);
		resultObject = fn.invoke(null, args.get(0).evaluate(symbols), args.get(1).evaluate(symbols));
		return resultObject;
	}

	private Object callUnaryFunction(Map<String, Double> symbols)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object resultObject;
		Method fn = Math.class.getMethod(fnName, double.class);
		resultObject = fn.invoke(null, args.get(0).evaluate(symbols));
		return resultObject;
	}
}
