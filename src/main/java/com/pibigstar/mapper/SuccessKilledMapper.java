package com.pibigstar.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pibigstar.domain.SuccessKilled;

public interface SuccessKilledMapper {
	/**
	 * 添加一条新的秒杀成功信息
	 * insert ignore into 当出现主键冲突的时候不插入
	 * @param seckillId
	 * @param userPhone
	 * @return 插入的行数，1：插入1行，0：插入失败
	 */
	@Insert("insert ignore into success_killed(seckill_id,user_phone,state)values(#{seckillId},#{userPhone},0)")
	@Options(useGeneratedKeys = true, keyProperty = "sekillId", keyColumn = "seckill_id") // id自动增长  
	int insert(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * 根据id拿到SuccessSeckilled并携带秒杀商品的对象
	 * @param seckillId
	 * @return
	 */
	@Select("select sk.seckill_id,sk.user_phone,sk.create_time,sk.state,"
			+ " s.seckill_id 'seckill.seckill_id',s.name 'seckill.name',s.number 'seckill.number',s.create_time 'seckill.create_time',s.start_time 'seckill.start_time',s.end_time 'seckill.end_time'"
			+ " from success_killed sk inner join seckill s on sk.seckill_id=s.seckill_id "
			+ " where sk.seckill_id=#{seckillId} and sk.user_phone=#{userPhone}")
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);

}
