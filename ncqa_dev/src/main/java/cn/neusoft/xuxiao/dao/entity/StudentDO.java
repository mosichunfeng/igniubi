package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class StudentDO {
	private int id;
	private String student_id;
	private String student_name;
	private String student_class;
	private String student_gender;
	private String student_tel;
}