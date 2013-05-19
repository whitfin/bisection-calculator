package com.zackehh.parse;

import java.util.Map;
import java.util.Stack;

class OperatorToken extends CalculationToken {

	CustomOperator operation;

	OperatorToken(String value, CustomOperator operation) {
		super(value);
		this.operation = operation;
	}

	double applyOperation(double... values) {
		return operation.applyOperation(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OperatorToken) {
			final OperatorToken t = (OperatorToken) obj;
			return t.getValue().equals(this.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
		final double[] operands = new double[operation.operandCount];
		for (int i = 0; i < operation.operandCount; i++) {
			operands[operation.operandCount - i - 1] = stack.pop();
		}
		stack.push(operation.applyOperation(operands));
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		Token before;
		while (!operatorStack.isEmpty() && (before = operatorStack.peek()) != null
				&& (before instanceof OperatorToken || before instanceof FunctionToken)) {
			if (before instanceof FunctionToken) {
				operatorStack.pop();
				output.append(before.getValue()).append(" ");
			} else {
				final OperatorToken stackOperator = (OperatorToken) before;
				if (this.isLeftAssociative() && this.getPrecedence() <= stackOperator.getPrecedence()) {
					output.append(operatorStack.pop().getValue()).append(" ");
				} else if (!this.isLeftAssociative() && this.getPrecedence() < stackOperator.getPrecedence()) {
					output.append(operatorStack.pop().getValue()).append(" ");
				} else {
					break;
				}
			}
		}
		operatorStack.push(this);
	}

	private boolean isLeftAssociative() {
		return operation.leftAssociative;
	}

	private int getPrecedence() {
		return operation.precedence;
	}
}