package com.pibigstar.exception;
/**
 * 秒杀关闭异常
 * @author pibigstar
 *
 */
public class SeckillCloseException extends SeckillException{

	private static final long serialVersionUID = 1L;

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillCloseException(String message) {
		super(message);
	}
	
}
