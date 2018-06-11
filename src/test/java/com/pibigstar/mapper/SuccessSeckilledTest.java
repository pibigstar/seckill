package com.pibigstar.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pibigstar.domain.SuccessKilled;
import com.pibigstar.mapper.SuccessKilledMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessSeckilledTest {

	@Autowired
	private SuccessKilledMapper successKilledMapper;
	
	@Test
	public void testInsert() {
		long id = 1001L;
		long phone = 15936010174L;
		System.out.println(successKilledMapper.insert(id, phone));
	}
	@Test
	public void testQueryByIdWithSeckill() {
		long id = 1001L;
		long phone = 15936010174L;
		SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(id, phone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
	
}
