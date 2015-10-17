package com.mike.arx.seekSave.daos.exceptions;

public class DAOCountryException extends DAOException {

	private static final long serialVersionUID = 1L;
	public DAOCountryException(String string) {
		super(string);
	}
	public DAOCountryException(String string,Throwable e) {
		super(string, e);
	}

}
