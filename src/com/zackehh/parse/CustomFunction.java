package com.zackehh.parse;

public abstract class CustomFunction {
	final int argc;

	final String name;

	protected CustomFunction(String name) throws InvalidCustomFunctionException {
		this.argc = 1;
		this.name = name;
		int firstChar = (int) name.charAt(0);
		if ((firstChar < 65 || firstChar > 90) && (firstChar < 97 || firstChar > 122)) {
			throw new InvalidCustomFunctionException("Functions have to start with a lowercase or uppercase character");
		}
	}

	protected CustomFunction(String name, int argumentCount) throws InvalidCustomFunctionException {
		this.argc = argumentCount;
		this.name = name;
	}

	public int getArgumentCount(){
		return argc;
	}
	
	public abstract double applyFunction(double... args);
}
