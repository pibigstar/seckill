package com.pibigstar.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pibigstar.domain.Seckill;
import com.pibigstar.mapper.SeckillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillMapperTest {
	
	@Autowired
	private SeckillMapper seckillMapper;
	
	@Test
	public void querySeckill() {
		Seckill seckill = seckillMapper.queryById(1001);
		System.out.println(seckill);
	}
	@Test
	public void reduceNumber() {
		int i =seckillMapper.reduceNumber(1001, new Date());
		System.out.println("i:"+i);
	}
	@Test
	public void queryAll() {
		List<Seckill> seckills = seckillMapper.queryAll(0, 100);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}

}
