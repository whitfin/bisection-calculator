package com.zackehh.parse;

import java.util.Stack;

abstract class Token {
	private final String value;

	Token(String value) {
		super();
		this.value = value;
	}

	String getValue() {
		return value;
	}

	abstract void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output);
}