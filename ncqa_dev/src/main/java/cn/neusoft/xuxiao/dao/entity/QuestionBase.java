package cn.neusoft.xuxiao.dao.entity;

import java.util.List;

import lombok.Data;

@Data
public class QuestionBase {
	private int id;
	private String name;
	private String description;
	private String start_time;
	private String end_time;
	
	private List<Question> question;
}