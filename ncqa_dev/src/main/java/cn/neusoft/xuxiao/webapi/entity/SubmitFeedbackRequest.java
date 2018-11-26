package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class SubmitFeedbackRequest {
    private int user_id;
    private String title;
    private String content;
}
