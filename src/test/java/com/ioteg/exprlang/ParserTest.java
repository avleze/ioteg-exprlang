package com.ioteg.exprlang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.exprlang.ast.BinaryExpressionAST;
import com.ioteg.exprlang.ast.CallExpressionAST;
import com.ioteg.exprlang.ast.ExpressionAST;
import com.ioteg.exprlang.ast.NumberExpressionAST;
import com.ioteg.exprlang.ast.UnaryExpressionAST;
import com.ioteg.exprlang.ast.VariableExpressionAST;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;

public class ParserTest {
	@Test
	public void testBinaryExpressions() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("2+4");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		NumberExpressionAST nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(4.0));

		exp = parser.parse("24.5*12.7");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(12.7));

		exp = parser.parse("24.5/12");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_DIV));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(12.0));

		exp = parser.parse("24.5-5");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUB));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(5.0));

		exp = parser.parse("24.5-5*4");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUB));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) bExp.getRhs();

		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(5.0));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testBinaryExpressionsWithError() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("24.5-5*$(");
		assertThat(exp, equalTo(null));
	}

	@Test
	public void testCallFunction() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("2+sqrt(4)");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		NumberExpressionAST nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));

		nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));

		exp = parser.parse("2 + pow(2, 4)");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("pow"));
		assertThat(cExp.getArgs().size(), equalTo(2));

		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(cExp.getArgs().get(1), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) cExp.getArgs().get(1);
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testVariableExpression() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("$(hola)*sqrt(4)");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));

		NumberExpressionAST nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testVariableExpressionWithErr() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("$hola)");
		assertThat(exp, equalTo(null));

		exp = parser.parse("$()");
		assertThat(exp, equalTo(null));

		exp = parser.parse("$(hola+4");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		NumberExpressionAST nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testParenthesisExpression() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("($(hola)*sqrt(4))");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));

		NumberExpressionAST nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testParenthesisExpressionWhithoutClosing() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("($(hola)*sqrt(4)");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));

		NumberExpressionAST nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testUnaryExpression() throws IOException, ExprLangParsingException {
		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("($(hola)*-sqrt(-4))");
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;

		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));
		assertThat(bExp.getRhs(), instanceOf(UnaryExpressionAST.class));
		UnaryExpressionAST uExp = (UnaryExpressionAST) bExp.getRhs();

		assertThat(uExp.getExpression(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) uExp.getExpression();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(UnaryExpressionAST.class));

		uExp = (UnaryExpressionAST) cExp.getArgs().get(0);
		assertThat(uExp.getUnaryOp(), equalTo(Token.TOK_OP_SUB));
		assertThat(uExp.getExpression(), instanceOf(NumberExpressionAST.class));

		NumberExpressionAST nExp = (NumberExpressionAST) uExp.getExpression();
		assertThat(nExp.getValue(), equalTo(4.0));

		exp = parser.parse("+sqrt(4)");
		assertThat(exp, instanceOf(CallExpressionAST.class));
		cExp = (CallExpressionAST) exp;
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}

	@Test
	public void testUnaryExpressionWithError() {
		ExprParser parser = new ExprParser();
		assertThrows(ExprLangParsingException.class, () -> {
			parser.parse("-sqrt((4)");
		});
	}

	@Test
	public void testCallExpressionWithNoArgumentsList() {
		ExprParser parser = new ExprParser();
		assertThrows(ExprLangParsingException.class, () -> {
			parser.parse("(hola)*sqrt(4)");
		});
	}

	@Test
	public void testCallExpressionWithIncompleteArgumentsList() {
		ExprParser parser = new ExprParser();
		assertThrows(ExprLangParsingException.class, () -> {
			parser.parse("(hola(4,))*sqrt(4)");
		});
	}

	@Test
	public void testCallExpressionWithUnexpectedArgumentsList() {
		ExprParser parser = new ExprParser();
		assertThrows(ExprLangParsingException.class, () -> {
			parser.parse("(hola(4~))*sqrt(4)");
		});
	}

	@Test
	public void testEvalExpression() throws ExprLangParsingException {
		Map<String, Double> symbols = new HashMap<>();

		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("pow(sin($(PI)),2)+pow(cos($(PI)),2)");
		symbols.put("PI", Math.PI);
		assertThat(exp.evaluate(symbols), equalTo(1.0));

		exp = parser.parse("1-2");
		assertThat(exp.evaluate(symbols), equalTo(-1.0));

		exp = parser.parse("4*2");
		assertThat(exp.evaluate(symbols), equalTo(8.0));

		exp = parser.parse("4/2");
		assertThat(exp.evaluate(symbols), equalTo(2.0));

		exp = parser.parse("-(-200)");
		assertThat(exp.evaluate(symbols), equalTo(200.0));
	}

	@Test
	public void testEvalFunctions() throws ExprLangParsingException {
		Map<String, Double> symbols = new HashMap<>();
		symbols.put("E", Math.E);
		symbols.put("PI", Math.PI);

		ExprParser parser = new ExprParser();
		ExpressionAST exp = parser.parse("pow(3,2)");
		assertThat(exp.evaluate(symbols), equalTo(9.0));

		exp = parser.parse("sqrt(4)");
		assertThat(exp.evaluate(symbols), equalTo(2.0));

		exp = parser.parse("abs(-2)");
		assertThat(exp.evaluate(symbols), equalTo(2.0));

		exp = parser.parse("max(-2,1)");
		assertThat(exp.evaluate(symbols), equalTo(1.0));

		exp = parser.parse("min(-2,1)");
		assertThat(exp.evaluate(symbols), equalTo(-2.0));

		exp = parser.parse("log10(max(-100,100))");
		assertThat(exp.evaluate(symbols), equalTo(2.0));

		exp = parser.parse("log(pow($(E),-3/2))");
		assertThat(exp.evaluate(symbols), equalTo(-1.5));

		exp = parser.parse("ceil($(E))");
		assertThat(exp.evaluate(symbols), equalTo(3.0));

		exp = parser.parse("floor($(E))");
		assertThat(exp.evaluate(symbols), equalTo(2.0));

		exp = parser.parse("round($(E))");
		assertThat(exp.evaluate(symbols), equalTo(3.0));

		exp = parser.parse("round($(E)-0.3)");
		assertThat(exp.evaluate(symbols), equalTo(2.0));

		exp = parser.parse("tan($(PI))");
		assertThat(exp.evaluate(symbols), greaterThanOrEqualTo(-0.0000001));
		assertThat(exp.evaluate(symbols), lessThanOrEqualTo(0.0000001));

		exp = parser.parse("atan(0)");
		assertThat(exp.evaluate(symbols), equalTo(0.0));

		exp = parser.parse("atan(1)");
		assertThat(exp.evaluate(symbols), equalTo(Math.PI / 4));

		exp = parser.parse("asin(1)");
		assertThat(exp.evaluate(symbols), equalTo(Math.PI / 2));

		exp = parser.parse("acos(0)");
		assertThat(exp.evaluate(symbols), equalTo(Math.PI / 2));
	}
}
