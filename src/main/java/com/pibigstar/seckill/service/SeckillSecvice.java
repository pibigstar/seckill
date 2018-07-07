package com.pibigstar.seckill.service;

import java.util.List;

import com.pibigstar.seckill.domain.Seckill;
import com.pibigstar.seckill.dto.Exposer;
import com.pibigstar.seckill.dto.SeckillExecution;
import com.pibigstar.seckill.exception.RepeatkillException;
import com.pibigstar.seckill.exception.SeckillCloseException;
import com.pibigstar.seckill.exception.SeckillException;

public interface SeckillSecvice {
	
	/**
	 * 查询所有的秒杀商品列表
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 根据id查询一个秒杀商品
	 * @param seckillId
	 * @return
	 */
	Seckill getOneById(long seckillId);

	/**
	 * 秒杀开启时输出秒杀接口地址
	 * 秒杀未开启时输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatkillException 重复执行秒杀异常
	 * @throws SeckillCloseException 秒杀关闭异常
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatkillException,SeckillCloseException;
	
	
}
