package com.kritsit.casetracker.server.domain;

public class Response {
	
	private String message;
	private Object body;
	
	public Response(String message, Object body){
		this.message = message;
		this.body = body;
	}
	
	public String getMessage(){
		return message;
	}
	
	public Object getBody(){
		return body;
	}
}
