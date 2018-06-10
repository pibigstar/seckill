-- 创建数据库
CREATE DATABASE seckill
-- 使用数据库
use seckill

-- 创建秒杀库存表
CREATE TABLE seckill(
	`seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
	`name` VARCHAR(120) NOT NULL COMMENT '商品名称',
	`number` INT NOT NULL COMMENT '库存数量',
	`start_time` datetime  NOT NULL COMMENT '秒杀开始时间',
	`end_time` datetime NOT NULL COMMENT '秒杀结束时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
-- 创建索引
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)

)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='秒杀库存表'

-- 初始化数据

insert into seckill(name,number,start_time,end_time)
VALUES
	('500元秒杀电脑',100,'2018-06-08 00:00:00','2018-06-09 00:00:00'),
	('1000元秒杀Iphone8',100,'2018-06-08 00:00:00','2018-06-09 00:00:00'),
	('300元秒杀小米5',100,'2018-06-07 00:00:00','2018-06-08 22:00:00'),
	('100元秒杀电子表',100,'2018-06-08 00:00:00','2018-06-09 00:00:00');




-- 秒杀成功明细表
CREATE TABLE success_killed(
	`seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
	`user_phone` BIGINT NOT NULL COMMENT '用户手机号',
	`state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识 -1：无效，0：成功，1：已付款',
	`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',

PRIMARY KEY(seckill_id,user_phone), /* 联合主键*/

key idx_create_time(create_time)

)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='秒杀成功明细表'

