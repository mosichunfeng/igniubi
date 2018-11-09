package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.Answer;
import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.RightAnswer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("IQuestionDao")
@Mapper
public abstract interface IQuestionDao
{
  public abstract int insertQuestion(List<Question> paramList);

  public abstract void insertAnswer(List<Answer> paramList);

  public abstract void insertRightAnswer(List<RightAnswer> paramList);
}