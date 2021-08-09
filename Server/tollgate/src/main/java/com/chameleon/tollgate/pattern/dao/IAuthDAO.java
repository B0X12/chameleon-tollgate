package com.chameleon.tollgate.pattern.dao;

public interface IAuthDAO {
	boolean setPattern(String id, String pattern, String ip) throws Exception;
	String getPattern(String id, String ip) throws Exception;
}