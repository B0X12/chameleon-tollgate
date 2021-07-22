package com.chameleon.tollgate.util.userHistory.dto;

import java.sql.ResultSet;

import lombok.Getter;

@Getter
public class historyRecord {
	private final String id;
	private final String timestamp;
	private final String factor;
	private final String pc;
	private final String result;
	
	public historyRecord(ResultSet rs) throws Exception {
			this.id = rs.getString(1);
			this.timestamp = rs.getString(2);
			this.factor = rs.getString(3);
			this.pc = rs.getString(4);
			this.result = rs.getString(5);
	}
}
