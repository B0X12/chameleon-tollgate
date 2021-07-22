package com.chameleon.tollgate.face.service;

public interface IAuthFaceService {
	boolean SendSignal(String id, long timestamp) throws Exception;
	boolean isOnDB(String id) throws Exception;
}
