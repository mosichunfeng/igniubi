package cn.neusoft.xuxiao.dao.entity;

import java.util.List;

import lombok.Data;

@Data
public class UserInfoAndBaseDO {
	private int id;
	private String student_id;
	private String student_name;
	private List<QuestionBase> base;
}
