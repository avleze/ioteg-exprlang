package com.ioteg.exprlang;

/**
 * <p>Token class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public enum Token {
	TOK_EOF,
	TOK_OP_MUL,
	TOK_OP_DIV,
	TOK_OP_SUM,
	TOK_OP_SUB,
	TOK_ID,
	TOK_NUMBER,
	TOK_OPEN_PAREN,
	TOK_CLOSED_PAREN,
	TOK_DOLLAR,
	TOK_COMMA,
	UNKOWN
}
