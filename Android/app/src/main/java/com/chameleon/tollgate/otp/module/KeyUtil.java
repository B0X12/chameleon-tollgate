package com.chameleon.tollgate.otp.module;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class KeyUtil {
    public static byte[] GetLittleEndianBytes(final long CalculateTimeValue)
    {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(CalculateTimeValue);
        byte [] result = buf.array(); //Big Endian
        Arrays.asList(result); // Big -> Little
        return result;
    }
}
