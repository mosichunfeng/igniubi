package cn.neusoft.xuxiao.service.inf;

import org.springframework.web.multipart.MultipartFile;

public abstract interface IQuestionService
{
  public abstract void insertDataToBase(MultipartFile paramMultipartFile);
}