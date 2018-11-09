package cn.neusoft.xuxiao.service.inf;

import org.springframework.web.multipart.MultipartFile;

public abstract interface IStudentService
{
  public abstract void parseExcel(MultipartFile paramMultipartFile);
}