package com.pibigstar.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pibigstar.seckill.domain.Seckill;
import com.pibigstar.seckill.dto.Exposer;
import com.pibigstar.seckill.dto.SeckillExecution;
import com.pibigstar.seckill.exception.RepeatkillException;
import com.pibigstar.seckill.exception.SeckillCloseException;
import com.pibigstar.seckill.exception.SeckillException;
import com.pibigstar.seckill.service.SeckillSecvice;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceTest {
	
	@Autowired
	private SeckillSecvice seckillSecvice;
	
	@Test
	public void testGetList() {
		List<Seckill> seckillList = seckillSecvice.getSeckillList();
		for (Seckill seckill : seckillList) {
			System.out.println(seckill);
		}
	}
	
	@Test
	public void testExportSeckillUrl() {
		Exposer exportSeckillUrl = seckillSecvice.exportSeckillUrl(1001);
		System.out.println(exportSeckillUrl);
	}
	
	@Test
	public void testExecuteSeckill() {
		String md5 = "aa09dd99c2a9ffd5e3f72d97ff5fa209";
		long phone = 15936230174L;
		SeckillExecution executeSeckill = seckillSecvice.executeSeckill(1001, phone, md5);
		System.out.println(executeSeckill);
	}
	
	/**
	 * 测试秒杀整个流程
	 */
	@Test
	public void testSeckill() {
		try {
			Exposer exposer = seckillSecvice.exportSeckillUrl(1002);
			if (exposer.isExposed()) {
				long phone = 15936120174L;
				String md5 = exposer.getMd5();
				SeckillExecution executeSeckill = seckillSecvice.executeSeckill(1001, phone, md5);
				System.out.println(executeSeckill);
			}else {
				System.out.println("秒杀未开启或已关闭");
			}
		} catch (RepeatkillException e) {
			System.out.println("重复秒杀");
		} catch (SeckillCloseException e) {
			System.out.println("秒杀关闭");
		} catch (SeckillException e) {
			System.out.println("业务异常");
		}catch (Exception e) {
			System.out.println("其他异常");
		}
	}
	

}
