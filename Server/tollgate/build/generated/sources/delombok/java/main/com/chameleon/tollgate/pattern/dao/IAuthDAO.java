package com.chameleon.tollgate.pattern.dao;

public interface IAuthDAO {
	boolean setPattern(String id, String pattern) throws Exception;
	String getPattern(String id) throws Exception;
}