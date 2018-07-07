package com.pibigstar.seckill.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pibigstar.seckill.domain.Seckill;

public interface SeckillMapper {
	
	@Select("select * from seckill")
	@Results({
		//如果不开启驼峰命名，就要手动这样设置
		@Result(property="seckillId",column="seckill_id"),
		@Result(property="createTime",column="create_time"),
		@Result(property="startTime",column="start_time"),
		@Result(property="endTime",column="end_time"),
	})
	List<Seckill> list();
	
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	@Update("update seckill set number = number-1 where seckill_id = #{seckillId} and start_time<=#{killTime} and end_time>=#{killTime} and number>0")
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * 根据id拿到秒杀商品
	 * @param seckillId
	 * @return
	 */
	@Select("select seckill_id,name,number,start_time,end_time,create_time from seckill where seckill_id=#{seckillId}")
	@Results({
		@Result(property="seckillId",column="seckill_id"),
		@Result(property="createTime",column="create_time"),
		@Result(property="startTime",column="start_time"),
		@Result(property="endTime",column="end_time"),
	})
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量拿到秒杀商品列表
	 * @param offet
	 * @param limit
	 * @return
	 */
	@Select("select seckill_id,name,number,start_time,end_time,create_time from seckill order by create_time desc limit #{offset},#{limit}")
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}
