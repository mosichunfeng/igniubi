package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class BindStudentInfoResponse extends BaseResponse{
	private int id;
	private String student_id;
	private String student_name;
	private String student_gender;
}