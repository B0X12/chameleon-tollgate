package com.chameleon.tollgate.rest;

import java.util.Timer;
import java.util.TimerTask;

import com.chameleon.tollgate.define.Property;

public class AuthStatus {
	private String id;
	private boolean verified;
	private Boolean success;
	private Timer timer;
	private TimerTask task;
	public String data;
	
	public AuthStatus(String id) {
		this.id = id;
		this.verified = false;
		this.success = false;
		this.timer = null;
		this.task = null;
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
	
	public Boolean isSuccess() {
		return this.success;
	}
	
	public Boolean waitVerify() throws InterruptedException {
		this.task = new TimerTask() {
			@Override
			public void run() {
				verify(null);
			}
		};
		this.timer = new Timer(true);
		this.timer.schedule(task, Property.LIMIT_TIME);
		
		while(!this.verified)
			Thread.sleep(100);
		return this.success;
	}
	
	public void verify(Boolean success) {
		this.verified = true;
		this.success = success;
		this.task.cancel();
		this.timer.cancel();
	}
	
	@Override
	public String toString() {
		return String.format("{ id = %s, verified = %s, success = %s", this.id, this.verified, this.success);
	}
}
