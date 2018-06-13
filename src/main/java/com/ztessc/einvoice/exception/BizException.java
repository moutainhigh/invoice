package com.ztessc.einvoice.exception;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = -8282365276679936350L;

	private String errorCode;

	public BizException() {
	} 

	public BizException(String message) {
		super(message);
	}
	
	public BizException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public BizException(String message, String errorCode, Throwable cause) {
		super(message,cause);
		this.errorCode = errorCode;
	}
	
	public BizException(String message,Throwable cause) {
		super(message,cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
