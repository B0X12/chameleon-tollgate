package com.chameleon.tollgate.face.dao;

public interface IAuthFaceDAO {
	boolean setFace(String id, String hashValue) throws Exception;
	String getFace(String id) throws Exception;
}