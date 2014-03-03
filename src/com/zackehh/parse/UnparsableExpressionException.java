package com.zackehh.parse;

public class UnparsableExpressionException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnparsableExpressionException(String expression, char c, int pos) {
		super("Unable to parse character '" + String.valueOf(c) + "' at position " + pos + " in expression '" + expression + "'");
	}

	public UnparsableExpressionException(String msg) {
		super(msg);
	}
}
