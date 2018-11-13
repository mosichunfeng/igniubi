package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class ActivityCodeDO {
	
	private int id;
	private int user_id;
	private int question_base_id;
	private String code;
	private String time;
}
