package com.chameleon.tollgate.util;

import com.chameleon.tollgate.util.exception.ParseException;

public class Util {
	public static boolean paresBoolean(int value) throws ParseException {
		return paresBoolean(value, false);
	}
	
	public static boolean paresBoolean(int value, boolean onlyBit) throws ParseException {
		if(onlyBit) {
			if(value == 0)
				return false;
			else if(value == 1)
				return true;
			else
				throw new ParseException("Value is not 0 or 1.");
		}
		return value==0?false:true;
	}
}
