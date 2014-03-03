package com.zackehh.parse;

public class UnknownFunctionException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnknownFunctionException(String functionName) {
		super("Unknown function: " + functionName);
	}
}
