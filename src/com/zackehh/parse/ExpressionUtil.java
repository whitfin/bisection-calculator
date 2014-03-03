package com.zackehh.parse;

import java.util.Locale;

public abstract class ExpressionUtil {
	public static String normalizeNumber(String number,Locale loc) throws UnparsableExpressionException{
		String result=number.replaceAll("e|E", "*10^");
		return result;
	}
	public static String normalizeNumber(String number) throws UnparsableExpressionException{
		return normalizeNumber(number, Locale.getDefault());
	}
}
