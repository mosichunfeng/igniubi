package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.*;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("IUserDao")
@Mapper
public abstract interface IUserDao {
	public abstract UserInfo findUserByOpenId(String paramString);

	public abstract Integer insertUserByOpenId(UserInfo userInfo);

	public abstract UserInfo findUserByStudentId(String student_id);

	public abstract void updateUserInfoById(UserInfo paramUserInfo);

	public abstract UserInfo findUserById(int paramInt);

	public abstract StudentDO findStudentById(String paramString);

	public abstract void bindStudentInfo(@Param("id") int paramInt, @Param("student_id") String paramString);

	public abstract List<ExamDO> getAnswerHistory(int user_id);

	public abstract int getQuestionCountByBaseId(int paramInt);

	public abstract Admin getAdminByUsername(String paramString);
	
	public abstract StudentDO findStudentByUid(int id);

	public abstract Register findRegisterByUid(int user_id);

	public abstract void register(Register registerDO);

	public abstract void saveFeedback(Feedback feedback);

	public abstract Register findRegisterByStudentId(String student_id);

	public abstract Register findRecentRegisterByStudentId(String student_id);

	public abstract List<StudentDO> findAllWGStudent();
}