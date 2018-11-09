package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class UserAnswerHistoryDO
{
  private int user_id;
  private String student_id;
  private String student_name;
  private int question_base_id;
  private String question_base_name;
}