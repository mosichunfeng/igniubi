package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import lombok.Data;

@Data
public class StartAnswerQuestionResponse {
	
	private int user_id;

	private QuestionBase question_base;
	
	/**
	 * 1：进行中 2：已完成
	 */
	private int status;
	
	private long last_minutes;

	private int grade;
}
