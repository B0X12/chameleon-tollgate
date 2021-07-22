package com.chameleon.tollgate.util.userHistory.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.database.exception.DBError;
import com.chameleon.tollgate.database.exception.DatabaseConnectException;
import com.chameleon.tollgate.util.userHistory.dao.exception.HistoryError;
import com.chameleon.tollgate.util.userHistory.dao.exception.HistoryResultException;
import com.chameleon.tollgate.util.userHistory.dto.historyRecord;

@Component
public class HistoryDAO extends SQLiteManager {
	public HistoryDAO(){
		super();
	}
	
	public boolean record(String id, String factor, String pc, int result) throws Exception {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
		
		PreparedStatement state = super.connection.prepareStatement("insert into "+Table.HISTORY+" values (?, ?, ?, ?, ?)");
		state.setString(1, id);
		state.setString(2, timestamp);
		state.setString(3, factor);
		state.setString(4, pc);
		state.setString(5, Integer.toString(result));
		
		state.executeUpdate();
		
		return true;
	}
	
	// 데이터 한번에 받아올 수 있게
	public ArrayList<historyRecord> get(String id) throws Exception {
		if(!isOpen()) {
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		}

		int count = getCountOf(Table.HISTORY, "id", id);
		if(count < 1) 
			throw new HistoryResultException(HistoryError.NO_HISTORY);

		ArrayList<historyRecord> recordList = new ArrayList<historyRecord>();
		PreparedStatement state = super.connection.prepareStatement("select * from " + Table.HISTORY + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();
		do {
			recordList.add(new historyRecord(rs));
		} while (rs.next());
			
			
		rs.close();
		
		return recordList;
	}
}
