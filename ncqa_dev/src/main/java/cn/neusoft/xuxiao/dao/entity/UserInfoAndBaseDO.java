package cn.neusoft.xuxiao.dao.entity;

import java.util.List;

import lombok.Data;

@Data
public class UserInfoAndBaseDO {
	private int id;
	private String student_id;
	private String student_name;
	private String title = "网工团队答题小程序";
	private List<QuestionBase> base;
}
