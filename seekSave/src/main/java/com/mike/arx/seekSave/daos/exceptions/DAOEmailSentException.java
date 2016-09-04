package com.mike.arx.seekSave.daos.exceptions;

public class DAOEmailSentException extends DAOException {

	private static final long serialVersionUID = 1L;

	public DAOEmailSentException(String string) {
		super(string);
	}
	public DAOEmailSentException(String string, Throwable e) {
		super(string, e);
	}


}
