package com.chameleon.tollgate.account.dao;

import com.chameleon.tollgate.account.dto.*;

public interface IAccountDAO {
	boolean checkIDPW(Account account);
	boolean insertAccountInfo(Account account);
	boolean insertMapPCInfo(MapPC map_pc);
}
