package com.mike.arx.seekSave.seeker.exceptions;

public class SeekerException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public SeekerException(String string){
		super(string);
	}
	public SeekerException(String string, Throwable e){
		super(string,e);
	}

}
