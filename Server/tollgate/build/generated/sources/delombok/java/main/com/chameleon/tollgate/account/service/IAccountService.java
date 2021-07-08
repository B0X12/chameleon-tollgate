package com.chameleon.tollgate.account.service;

import com.chameleon.tollgate.account.dto.*;

public interface IAccountService {
	public boolean logIn(Account account);

	public boolean signUp(Account account);

	public boolean mappingPC(MapPC map_pc);

}
