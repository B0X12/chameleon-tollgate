package com.chameleon.tollgate.rest;

import java.util.ArrayList;

import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.NoUserException;

public class AuthList {
	private ArrayList<AuthStatus> status;
	
	public AuthList() {
		this.status = new ArrayList<AuthStatus>();
	}
	
	public void add(String id) {
		try {
			verify(id, false);
		} catch (NoUserException ex) { }
		
		AuthStatus stat = new AuthStatus(id);
		status.add(stat);
	}
	
	@SuppressWarnings("finally")
	public void remove(String id) {
		try {
			AuthStatus stat = findStatus(id);
			this.status.remove(stat);
		} finally {
			return;
		}
	}
	
	public Boolean waitVerify(String id) throws NoUserException, InterruptedException {
		AuthStatus as = findStatus(id);
		return as.waitVerify();
	}
	
	public AuthStatus findStatus(String id) throws NoUserException {
		for(AuthStatus as : this.status) {
			if(as.getId().equals(id)) {
				return as;
			}
		}
		throw new NoUserException(AuthError.NO_USER);
	}
	
	public void verify(String id, boolean success) throws NoUserException {
		AuthStatus as = findStatus(id);
		as.verify(success);
	}
}