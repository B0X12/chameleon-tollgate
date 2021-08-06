package com.chameleon.tollgate.otp.module;


public interface IKeyProvider {
	byte[] ComputeHmac(final int hashtype,final byte[] data);
}
