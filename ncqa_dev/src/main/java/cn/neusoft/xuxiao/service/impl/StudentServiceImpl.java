package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.StudentDO;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import cn.neusoft.xuxiao.utils.ExcelUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("iStudentServiceImpl")
public class StudentServiceImpl
  implements IStudentService
{

  @Resource(name="IStudentDao")
  private IStudentDao studentDao;

  public void parseExcel(MultipartFile file)
  {
    String fileName = file.getOriginalFilename();
    String[] columns = { "编号", "学号", "姓名", "班级", "性别" };
    InputStream is = null;
    try {
      is = file.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    List list = ExcelUtil.parseExcel(fileName, is, columns);
    List studentList = new ArrayList();
    for (Map map : list) {
      StudentDO student = new StudentDO();
      student.setStudent_id((String)map.get("学号"));
      student.setStudent_name((String)map.get("姓名"));
      student.setStudent_class((String)map.get("班级"));
      student.setStudent_gender((String)map.get("性别"));

      studentList.add(student);
    }

    this.studentDao.insertStudent(studentList);
  }
}