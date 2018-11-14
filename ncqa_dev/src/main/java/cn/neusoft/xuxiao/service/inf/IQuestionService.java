package cn.neusoft.xuxiao.service.inf;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;

public abstract interface IQuestionService {
	public abstract void insertDataToBase(MultipartFile paramMultipartFile);
	
	public abstract List<QuestionBase> getAllQuestionBase();
	
	public abstract List<Question> getAllQuestionByBaseId();
}