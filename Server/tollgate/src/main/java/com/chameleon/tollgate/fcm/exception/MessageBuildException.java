package com.chameleon.tollgate.fcm.exception;

import com.chameleon.tollgate.rest.exception.BaseRuntimeException;

@SuppressWarnings("serial")
public class MessageBuildException extends BaseRuntimeException {
	public MessageBuildException(FCMError error) {
		super(error);
	}
}