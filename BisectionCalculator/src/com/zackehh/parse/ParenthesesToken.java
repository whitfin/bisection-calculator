package com.zackehh.parse;

import java.util.Stack;

class ParenthesesToken extends Token {

	ParenthesesToken(String value) {
		super(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParenthesesToken) {
			final ParenthesesToken t = (ParenthesesToken) obj;
			return t.getValue().equals(this.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	boolean isOpen() {
		return getValue().equals("(") || getValue().equals("[") || getValue().equals("{");
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		if (this.isOpen()) {
			operatorStack.push(this);
		} else {
			Token next;
			while ((next = operatorStack.peek()) instanceof OperatorToken || next instanceof FunctionToken
					|| (next instanceof ParenthesesToken && !((ParenthesesToken) next).isOpen())) {
				output.append(operatorStack.pop().getValue()).append(" ");
			}
			operatorStack.pop();
		}
	}
}