package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.Admin;
import cn.neusoft.xuxiao.dao.entity.StudentDO;
import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("IUserDao")
@Mapper
public abstract interface IUserDao
{
  public abstract UserInfo findUserByOpenId(String paramString);

  public abstract Integer insertUserByOpenId(String paramString);

  public abstract void updateUserInfoById(UserInfo paramUserInfo);

  public abstract UserInfo findUserById(int paramInt);

  public abstract StudentDO findStudentById(String paramString);

  public abstract void bindStudentInfo(@Param("id") int paramInt, @Param("student_id") String paramString);

  public abstract List<UserAnswerHistoryDO> getAnswerHistory(int paramInt);

  public abstract int getQuestionCountByBaseId(int paramInt);

  public abstract Admin getAdminByUsername(String paramString);
}