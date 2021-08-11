package com.chameleon.tollgate.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionTime {
	private String id;
	private long timestamp;

	public SessionTime(String id, long timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SessionTime))
			return false;
		SessionTime st = (SessionTime)obj;
		if(st.id.equals(this.id) && st.timestamp == this.timestamp)
			return true;
		return false;
	}
}
