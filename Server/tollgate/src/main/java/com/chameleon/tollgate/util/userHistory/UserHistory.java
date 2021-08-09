package com.chameleon.tollgate.util.userHistory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.util.userHistory.dao.HistoryDAO;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;
import com.chameleon.tollgate.util.userHistory.dto.HistoryRecord;

@Service
public class UserHistory {
	@Autowired
	HistoryDAO dao;
	
	public boolean write(String id, HistoryFactor factor, String sid, HistoryResult result) {		
		dao.open();
		boolean daoResult;
		try {
			daoResult = dao.record(id, factor.getFactor(), dao.getAlias(sid), result.getCode());
			dao.commit();
			return daoResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(dao.isOpen())
				dao.close();	
		}
		return false;
	}
	
	public ArrayList<HistoryRecord> get(String id) throws Exception {		
		dao.open(true);
		ArrayList<HistoryRecord> recordList = dao.get(id);
		dao.close();
		
		return recordList;
	}
}
