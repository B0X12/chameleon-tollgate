package com.chameleon.tollgate.otp.module;


public interface IKeyProvider {
	byte[] ComputeHmac(int hashtype, byte[] data);
}
