package com.pibigstar.seckill.enums;

/**
 * 秒杀状态枚举
 * @author pibigstar
 *
 */
public enum SeckillStateEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀失败"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据篡改异常");
	
	private int state;
	private String stateInfo;
	
	
	
	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
	public static SeckillStateEnum stateOf(int index) {
		for (SeckillStateEnum state : values()) {
			if (state.getState()==index) {
				return state;
			}
		}
		return null;
	}
	

}