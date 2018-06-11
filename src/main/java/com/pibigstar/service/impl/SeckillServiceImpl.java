package com.pibigstar.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pibigstar.domain.Seckill;
import com.pibigstar.domain.SuccessKilled;
import com.pibigstar.dto.Exposer;
import com.pibigstar.dto.SeckillExecution;
import com.pibigstar.enums.SeckillStateEnum;
import com.pibigstar.exception.RepeatkillException;
import com.pibigstar.exception.SeckillCloseException;
import com.pibigstar.exception.SeckillException;
import com.pibigstar.mapper.SeckillMapper;
import com.pibigstar.mapper.SuccessKilledMapper;
import com.pibigstar.service.SeckillSecvice;
import com.pibigstar.utils.MD5Util;

@Service
public class SeckillServiceImpl implements SeckillSecvice{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillMapper seckillMapper;
	@Autowired
	private SuccessKilledMapper successKilledMapper;

	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillMapper.queryAll(0, 10);
	}

	@Override
	public Seckill getOneById(long seckillId) {
		return seckillMapper.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillMapper.queryById(seckillId);
		if (seckill==null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		
		Date nowTime = new Date();
		if (nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()) {
			return new Exposer(false,seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = MD5Util.getMD5(seckillId);//TODO 回头再写标志
		return new Exposer(true, md5, seckillId);
	}

	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatkillException, SeckillCloseException {
		
		if (md5==null||!md5.equals(MD5Util.getMD5(seckillId))) {
			throw new SeckillException("MD5值不合法");
		}
		try {
			//执行秒杀，减少库存
			Date nowTime = new Date();
			int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);
			if (updateCount<=0) {
				//没有更新到记录，秒杀结束
				throw new SeckillCloseException("秒杀结束");
			}else {
				//记录购买行为
				int insertCount = successKilledMapper.insert(seckillId, userPhone);
				if (insertCount<=0) {
					//重复插入（主键为seckill和userphone联合主键）
					//返回0，重复秒杀
					throw new RepeatkillException("重复秒杀");
				}else {
					//返回1，秒杀成功
					SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
				}
			
			}
		}catch (SeckillCloseException e1) {
			throw e1;
		}catch (RepeatkillException e2) {
			throw e2;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译期异常 转换为了运行期异常
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
		
	}

	
}
