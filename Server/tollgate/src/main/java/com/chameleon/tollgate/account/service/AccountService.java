package com.chameleon.tollgate.account.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.account.dao.AccountDAO;
import com.chameleon.tollgate.account.dto.*;
import com.chameleon.tollgate.account.exception.UserAlreadyExistException;

@Service
public class AccountService implements IAccountService {
	public AccountService() {
		super();
	}

	@Autowired
	private AccountDAO dao;

	@Override
	public boolean logIn(Account account) throws SQLException {
		return dao.checkIDPW(account);
	}

	@Override
	public boolean signUp(Account account) throws SQLException {
		try {
			if (dao.insertAccountInfo(account) == true)
				return true;
		} catch (UserAlreadyExistException uae) {
			return false;
		}
		return false;
	}

	@Override
	public boolean mappingPC(MapPC map_pc) {
		return dao.insertMapPCInfo(map_pc);
	}

	@Override
	public String getUserByUID(String uid) throws SQLException {
		return dao.getUserByUID(uid);
	}
	
	@Override
	public boolean unmapUserByUID(String uid){
		return dao.deleteMapPCInfo(uid);
	}

	@Override
	public int getFactorFlagByUser(String user) throws SQLException {
		return dao.getFactorFlagByUser(user);
	}
}
