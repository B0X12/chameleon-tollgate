package com.chameleon.tollgate.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.account.dao.AdAccountDAO;
import com.chameleon.tollgate.account.exception.AccountError;
import com.chameleon.tollgate.account.exception.InvalidIDException;
import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.LoginCode;

@Service
public class AdAccountService implements IAdAccountService {
	@Autowired
	AdAccountDAO dao;
	
	@Override
	public boolean login(String id, String pwd, String ip) throws Exception {
		try {
			this.dao.open(true);
			int count = this.dao.getCountOf(Table.ACCOUNT, "id", id);
			if(count != 1) {
				TollgateLog.w(ip, id, LogFactor.LOGIN, LoginCode.NO_USER, "There are no user.");
				this.dao.close();
				throw new InvalidIDException(AccountError.NO_USER);
			}
			
			String exist = this.dao.getPassword(id);
			this.dao.close();
			
			if(exist.compareTo(pwd) == 0) {
				TollgateLog.w(ip, id, LogFactor.LOGIN, LoginCode.AD_LOGIN, "Login success.");
				return true;
			}
			TollgateLog.w(ip, id, LogFactor.LOGIN, LoginCode.AD_LOGIN_FAILED, "Login failed.");
			return false;
		} finally {
			this.dao.close();
		}
	}

	@Override
	public boolean logout(String id, String ip) throws Exception {
		try {
			this.dao.open();
			int result = this.dao.removeToken(id);
			this.dao.commit();
			this.dao.close();
			
			if(result == 1) {
				TollgateLog.w(ip, id, LogFactor.LOGIN, LoginCode.AD_LOGOUT, "Logout.");
				return true;
			}
			TollgateLog.w(ip, id, LogFactor.LOGIN, LoginCode.AD_LOGOUT_FAILED, "Logout failed.");
			return false;
		} finally {
			this.dao.close();
		}
	}

	@Override
	public boolean mapAndroid(String id, String token, String ip) throws Exception {
		try {
			this.dao.open();
			if(this.dao.isExistToken(id, token)) {
				this.dao.close();
				return true;
			}
			
			if(this.dao.getCountOf(Table.MAP_ANDROID, "id", id) == 1) {
				if(this.dao.removeToken(id) != 1) {
					this.dao.close();
					return false;
				}
				TollgateLog.i(ip,  id,  LogFactor.LOGIN, LoginCode.AD_LOGIN, "Remove user's android token.");
			}
			
			if(this.dao.setToken(id, token) != 1) {
				this.dao.close();
				return false;
			}
			TollgateLog.i(ip,  id,  LogFactor.LOGIN, LoginCode.AD_LOGIN, "Add user's android token.");
			this.dao.commit();
			this.dao.close();
			
			return true;
		} finally {
			this.dao.close();
		}
	}

	@Override
	public String getID(String token) throws Exception {
		try {
			this.dao.open();
	   		if(this.dao.getCountOf(Table.MAP_ANDROID, "token", token) != 1)
				return null;
			String result = this.dao.getID(token);
			this.dao.close();
			return result;
		} finally {
			this.dao.close();
		}
	}
}