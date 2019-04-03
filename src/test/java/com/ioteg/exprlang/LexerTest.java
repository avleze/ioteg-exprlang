package com.ioteg.exprlang;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

public class LexerTest {
	
	@Test
	public void testArithmeticOperators() throws IOException {
		ExprLexer lex = new ExprLexer("2+4");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("2"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OP_SUM));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OP_SUM));
		assertThat(lex.getCurrentMatch(), equalTo("+"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("4"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
		
		lex = new ExprLexer("2*4");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("2"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OP_MUL));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OP_MUL));
		assertThat(lex.getCurrentMatch(), equalTo("*"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("4"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
		
		lex = new ExprLexer("2-4");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("2"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OP_SUB));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OP_SUB));
		assertThat(lex.getCurrentMatch(), equalTo("-"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("4"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
		
		lex = new ExprLexer("2/4");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("2"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OP_DIV));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OP_DIV));
		assertThat(lex.getCurrentMatch(), equalTo("/"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("4"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
	}
	
	@Test
	public void testIdentifiers() throws IOException {
		ExprLexer lex = new ExprLexer("2+$(hola)");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("2"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OP_SUM));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OP_SUM));
		assertThat(lex.getCurrentMatch(), equalTo("+"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_DOLLAR));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_DOLLAR));
		assertThat(lex.getCurrentMatch(), equalTo("$"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo("("));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentMatch(), equalTo("hola"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo(")"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
		
		lex = new ExprLexer("sqrt($(hola))");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentMatch(), equalTo("sqrt"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo("("));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_DOLLAR));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_DOLLAR));
		assertThat(lex.getCurrentMatch(), equalTo("$"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo("("));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentMatch(), equalTo("hola"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo(")"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo(")"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
	}
	
	@Test
	public void testFloatingPointNumbers() throws IOException {
		ExprLexer lex = new ExprLexer("20.45+$(hola)");
		assertThat(lex.getNextToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_NUMBER));
		assertThat(lex.getCurrentMatch(), equalTo("20.45"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OP_SUM));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OP_SUM));
		assertThat(lex.getCurrentMatch(), equalTo("+"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_DOLLAR));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_DOLLAR));
		assertThat(lex.getCurrentMatch(), equalTo("$"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_OPEN_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo("("));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentMatch(), equalTo("hola"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_CLOSED_PAREN));
		assertThat(lex.getCurrentMatch(), equalTo(")"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
	}
	
	@Test
	public void testUnkown() throws IOException {
		ExprLexer lex = new ExprLexer(".~Unkown");
		assertThat(lex.getNextToken(), equalTo(Token.UNKOWN));
		assertThat(lex.getCurrentToken(), equalTo(Token.UNKOWN));
		assertThat(lex.getCurrentMatch(), equalTo("."));
		assertThat(lex.getNextToken(), equalTo(Token.UNKOWN));
		assertThat(lex.getCurrentToken(), equalTo(Token.UNKOWN));
		assertThat(lex.getCurrentMatch(), equalTo("~"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentToken(), equalTo(Token.TOK_ID));
		assertThat(lex.getCurrentMatch(), equalTo("Unkown"));
		assertThat(lex.getNextToken(), equalTo(Token.TOK_EOF));
	}
	
}
