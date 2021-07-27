package com.chameleon.tollgate.util.tollgateLog.dto.code;

public enum faceCode implements LogCode {
	START_AUTH("Face Authentication Start", 1),
	TRAIN_AUTH("Start Authentication with train mode", 2),
	AUTH_AUTH("Start Authentication with auth mode", 3),
	UNKNOWN_AUTH("No Face", 4),
	FACE_NOT_FOUND("No Face", 5),
	FACE_WRONG("Wrong Face Model file", 6),
	FACE_REGISTER("New Face Model Created", 7),
	FACE_VERIFIED("Face Matched with Existing face model", 7);

	private final String log;
	private final int code;
	private faceCode(String log, int code) {
		this.log = log;
		this.code = code;
	}
	
	@Override
	public int getErrorCode() {
		return code;
	}
	
	@Override
	public String getErrorMessage() {
		return log;
	}

}
