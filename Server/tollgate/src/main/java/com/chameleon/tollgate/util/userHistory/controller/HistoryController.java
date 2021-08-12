package com.chameleon.tollgate.util.userHistory.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.util.userHistory.HistoryPack;
import com.chameleon.tollgate.util.userHistory.UserHistory;
import com.chameleon.tollgate.util.userHistory.dto.HistoryRecord;


@RestController
public class HistoryController {
	@Autowired
	UserHistory history;

	@PostMapping(path=Path.HISTORY+"{id}")
	public ResponseEntity<Response<ArrayList<HistoryRecord>>> GetHistory(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, 
			@RequestBody HistoryPack entry) throws Exception{
		if (!userAgent.equals(Property.USER_AGENT)) {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
			
		
		ArrayList<HistoryRecord> historyList = history.get(id);
		Collections.reverse(historyList);
		
		System.out.println(entry.getTimestamp());
		return new ResponseEntity<Response<ArrayList<HistoryRecord>>>(
				new Response<ArrayList<HistoryRecord>>(HttpStatus.OK, historyList, entry.getTimestamp()),
				HttpStatus.OK);
			
	}
}
