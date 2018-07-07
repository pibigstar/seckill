package com.pibigstar.seckill.utils;

import org.springframework.util.DigestUtils;

/**
 * MD5工具类
 * @author pibigstar
 *
 */
public class MD5Util {
	//盐，用于混交md5
	private static final String slat = "pibigstar%^&*(*%%$";
	/**
	 * 生成md5
	 * @param seckillId
	 * @return
	 */
	public static String getMD5(long seckillId) {
		String base = seckillId +"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
