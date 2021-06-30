package com.chameleon.tollgate.pattern;

public class AuthStatus {
	private String id;
	private boolean verified;
	private boolean success;
	
	public AuthStatus(String id) {
		this.id = id;
		this.verified = false;
		this.success = false;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	public boolean isVerified() {
		return this.verified;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	@Override
	public String toString() {
		return String.format("{ id = %s, verified = %s, success = %s", this.id, this.verified, this.success);
	}
}
