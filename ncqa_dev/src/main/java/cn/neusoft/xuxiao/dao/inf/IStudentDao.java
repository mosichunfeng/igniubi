package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.StudentDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("IStudentDao")
@Mapper
public abstract interface IStudentDao
{
  public abstract void insertStudent(List<StudentDO> paramList);
}