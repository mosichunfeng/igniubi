package cn.neusoft.xuxiao.webapi.entity;

import java.util.Map;

import lombok.Data;

@Data
public class SubmitContentRequest
{
  private int user_id;
  private int question_base_id;
  private Map<Integer, String> map;
  private String end_time;
}