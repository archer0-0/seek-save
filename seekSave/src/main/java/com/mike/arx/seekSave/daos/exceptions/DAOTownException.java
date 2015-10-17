package com.mike.arx.seekSave.daos.exceptions;

public class DAOTownException extends DAOException {

	private static final long serialVersionUID = 1L;
	public DAOTownException(String string) {
		super(string);
	}
	public DAOTownException(String string,Throwable throwable) {
		super(string, throwable);
	}

}
