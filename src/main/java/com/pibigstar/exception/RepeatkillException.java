package com.pibigstar.exception;

/**
 * 重复执行秒杀异常（运行期异常）
 * @author pibigstar
 *
 */
public class RepeatkillException extends SeckillException{

	private static final long serialVersionUID = 1L;

	public RepeatkillException(String message) {
		super(message);
	}

	public RepeatkillException(String message, Throwable cause) {
		super(message, cause);
	}

}
