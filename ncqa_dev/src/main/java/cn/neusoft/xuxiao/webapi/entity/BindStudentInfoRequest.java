package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class BindStudentInfoRequest{
	private int id;
	private String student_id;
	private String student_name;
}