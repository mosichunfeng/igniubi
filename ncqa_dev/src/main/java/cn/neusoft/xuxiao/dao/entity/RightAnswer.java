package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class RightAnswer
{
  private int id;
  private int question_id;
  private String answer_index;
  private String answer_content;
}