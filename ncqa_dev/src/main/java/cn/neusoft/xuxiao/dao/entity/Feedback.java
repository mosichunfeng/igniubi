package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class Feedback {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private String record_time;
}
