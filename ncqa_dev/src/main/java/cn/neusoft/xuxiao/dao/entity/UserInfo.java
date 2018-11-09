package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class UserInfo
{
  private int id;
  private String open_id;
  private String account;
  private String student_id;
  private String username;
  private String region;
  private String sex;
  private int age;
  private String head_url;
}