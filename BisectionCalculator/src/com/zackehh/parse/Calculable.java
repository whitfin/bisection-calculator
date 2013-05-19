package com.zackehh.parse;

public interface Calculable {
	public double calculate();
	public double calculate(double... variableValues);
	public String getExpression();
	public void setVariable(String name, double value);
}
