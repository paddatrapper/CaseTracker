package com.kritsit.casetracker.server.domain;

public class Response {
	
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
