package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.*;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("IQuestionDao")
@Mapper
public abstract interface IQuestionDao {
	public abstract int insertQuestion(List<Question> paramList);

	public abstract void insertAnswer(List<Answer> paramList);

	public abstract void insertRightAnswer(List<RightAnswer> paramList);
	
	public abstract List<QuestionBase> getAllQuestionBase();
	
	public abstract ActivityCodeDO getActivityCode(@Param("user_id") int user_id, @Param("base_id") int base_id);
	
	public abstract void insertActivityCode(ActivityCodeDO activityDO);
	
	public abstract QuestionBase getQuestionBaseById(int id);

	public abstract ExamDO findExamHistoryByCode(String code);
	
	public abstract void insertExamHistory(ExamDO examDO);
	
	public abstract List<Question> findQuestionListByBaseId(int base_id);
	
	public abstract List<Answer> findAnswerListByQuestionId(int question_id);

	public abstract void updateGradeByCode(ExamDO examDO);

	public abstract RightAnswer findRightAnswerByQuestionId(int question_id);

	public abstract List<Question> findRandQuestionListByBaseId(int base_id);

	public abstract List<GradeDO> findGradeListByBaseId(int base_id);
}