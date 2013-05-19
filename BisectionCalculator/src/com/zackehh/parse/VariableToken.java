package com.zackehh.parse;

import java.util.Map;
import java.util.Stack;

class VariableToken extends CalculationToken {

	VariableToken(String value) {
		super(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VariableToken) {
			return super.getValue().equals(((VariableToken) obj).getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.getValue().hashCode();
	}

	@Override
	void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
		double value = variableValues.get(this.getValue());
		stack.push(value);
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		output.append(this.getValue()).append(" ");
	}
}