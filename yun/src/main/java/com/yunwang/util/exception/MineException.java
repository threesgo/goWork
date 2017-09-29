package com.yunwang.util.exception;

public class MineException extends RuntimeException{

	/**
	 * 自定义Exception
	 */
	private static final long serialVersionUID = -3862759869299243705L;

	public MineException() {
		super();
	}


	public MineException(String message, Throwable cause) {
		super(message, cause);
	}

	public MineException(String message) {
		super(message);
	}

	public MineException(Throwable cause) {
		super(cause);
	}

	
	
}
