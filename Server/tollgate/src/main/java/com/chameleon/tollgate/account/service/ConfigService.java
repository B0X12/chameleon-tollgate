package com.chameleon.tollgate.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.account.dao.ConfigDAO;
import com.chameleon.tollgate.database.define.Factor;
import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.NoUserException;

@Service
public class ConfigService implements IConfigService {
	@Autowired
	ConfigDAO dao;
	
	public boolean isInitAndroid(String id) throws Exception {
		try {
			dao.open();
			int count = dao.getCountOf(Table.MAP_ANDROID, "id", id);
			dao.close();
			if(count == 1)
				return true;
			return false;
		} finally {
			dao.close();
		}
	}
	
	public boolean isInitFactor(String id, Factor factor) throws Exception {
		try {
			dao.open();
			int count = 0;
			count = dao.getCountOf(Table.INIT_FACTOR, "id", id);
			if(count == 0)
				throw new NoUserException(AuthError.NO_USER);
			boolean result = dao.isInitFactor(id, factor);
			dao.close();
			return result;
		} finally {
			this.dao.close();
		}
	}
	
	public boolean setInitFactor(String id, Factor factor, boolean value) throws Exception {
		try {
			dao.open();
			int count = dao.setInitFactor(id, factor, value);
			if(count == 1)
				dao.commit();
			dao.close();
			if(count == 1)
				return true;
			return false;
		} finally {
			this.dao.close();
		}
	}
}
