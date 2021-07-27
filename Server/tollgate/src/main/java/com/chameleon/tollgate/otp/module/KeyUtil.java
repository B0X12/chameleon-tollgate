package com.chameleon.tollgate.otp.module;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class KeyUtil {
	static public byte[] GetLittleEndianBytes(long CalculateTimeValue)
	{
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putLong(CalculateTimeValue);
		byte [] result = buf.array(); //Big Endian
		Arrays.asList(result); // Big -> Little
		return result;
	}
}
