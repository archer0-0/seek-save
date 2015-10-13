package com.mike.arx.seekSave.seeker;
/**
 * Exception to error on seeker process
 * @author marquero
 *
 */
public class SeekException extends Exception {

	private static final long serialVersionUID = 1L;
	public SeekException(String string) {
		super(string);
	}
	public  SeekException(String string, Throwable e) {
		super(string, e);
	}
	

}
