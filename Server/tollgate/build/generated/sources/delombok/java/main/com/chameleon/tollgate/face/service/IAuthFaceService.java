package com.chameleon.tollgate.face.service;

public interface IAuthFaceService {
	boolean SendSignal(String id) throws Exception;
	boolean isOnDB(String id) throws Exception;
}
