package com.mike.arx.seekSave.daos.exceptions;

/**
 * Generic exception for DB consistency problems 
 * @author marquero
 *
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -2956805206943047138L;
	public DAOException(String string){
		super(string);
	}
	

}
