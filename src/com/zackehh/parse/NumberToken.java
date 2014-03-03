package com.zackehh.parse;

import android.annotation.SuppressLint;
import java.util.Map;
import java.util.Stack;

// Suppress warning with toLowerCase(), bugs are caught
@SuppressLint("DefaultLocale")
class NumberToken extends CalculationToken {

	private final double doubleValue;

	NumberToken(String value) {
		super(value);
		if (value.indexOf('E') > 0 || value.indexOf('e') > 0){
			// Scientific notation as requested in EXP-17
			value = value.toLowerCase();
			int pos = value.indexOf('e');
			double mantissa = Double.parseDouble(value.substring(0,pos));
			double exponent = Double.parseDouble(value.substring(pos + 1));
			this.doubleValue = mantissa * Math.pow(10, exponent);
		}else{
			this.doubleValue = Double.parseDouble(value);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NumberToken) {
			final NumberToken t = (NumberToken) obj;
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
		stack.push(this.doubleValue);
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		output.append(this.getValue()).append(' ');
	}
}