package cn.neusoft.xuxiao.dao.entity;

import java.util.List;

import lombok.Data;

@Data
public class Question {
	private int id;
	private int question_index;
	private int question_base_id;
	private int select_type;
	private String content;
	private List<Answer> answer_list;
}