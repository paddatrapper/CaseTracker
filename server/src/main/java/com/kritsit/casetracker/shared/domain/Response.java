package com.kritsit.casetracker.shared.domain;

import java.io.Serializable;

public class Response implements Serializable {

	private int statusCode;
	private Object body;
	
	public Response(int statusCode, Object body){
		this.statusCode = statusCode;
		this.body = body;
	}
	
	public int getMessage(){
		return statusCode;
	}
	
	public Object getBody(){
		return body;
	}
}
