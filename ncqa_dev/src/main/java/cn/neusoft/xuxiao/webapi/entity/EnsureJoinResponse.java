package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class EnsureJoinResponse extends BaseResponse{

	private String student_name;
	private String question_base_name;
	private String activity_code;
}
