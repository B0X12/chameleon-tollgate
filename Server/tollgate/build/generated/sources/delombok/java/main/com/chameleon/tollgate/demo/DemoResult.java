package com.chameleon.tollgate.demo;

public class DemoResult {
	private String result;
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return this.result;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.result);
	}
}
