package com.pibigstar.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pibigstar.seckill.domain.Seckill;
import com.pibigstar.seckill.mapper.SeckillMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {
	@Autowired
	private SeckillMapper seckillMapper;
	
	@Test
	public void contextLoads() {
	}
	
	

}
