package com.chameleon.tollgate.util.userHistory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.util.userHistory.dao.HistoryDAO;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;
import com.chameleon.tollgate.util.userHistory.dto.historyRecord;

@Service
public class UserHistory {
	@Autowired
	HistoryDAO dao;
	
	public boolean write(String id, HistoryFactor factor, String pc, HistoryResult result) throws Exception {		
		dao.open();
		boolean daoResult = dao.record(id, factor.getFactor(), pc, result.getCode());
		dao.commit();
		dao.close();
		
		return daoResult;
	}
	
	public ArrayList<historyRecord> get(String id) throws Exception {		
		dao.open(true);
		ArrayList<historyRecord> recordList = dao.get(id);
		dao.close();
		
		return recordList;
	}
}
