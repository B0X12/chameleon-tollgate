package com.chameleon.tollgate.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.account.dao.AccountDAO;
import com.chameleon.tollgate.account.dto.*;

@Service
public class AccountService implements IAccountService {
	public AccountService() {
		super();
	}

	@Autowired
	private AccountDAO dao;

	@Override
	public boolean logIn(Account account) {
		if (dao.checkIDPW(account) == true)
			return true;

		return false;
	}

	@Override
	public boolean signUp(Account account) {
		if (dao.insertAccountInfo(account) == true)
			return true;

		return false;
	}

	@Override
	public boolean mappingPC(MapPC map_pc) {
		if (dao.insertMapPCInfo(map_pc) == true)
			return true;

		return false;
	}

}
