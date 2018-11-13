package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class ExamDO {

	private int id;
	
	private int user_id;
	
	private int question_base_id;
	
	private String activity_code;
	
	private String start_time;
	
	private String end_time;
	
	private int status;
	
	private int grade;
}
