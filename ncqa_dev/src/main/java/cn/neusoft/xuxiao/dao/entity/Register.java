package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class Register {
    private int id;

    /**
     * 学号
     */
    private String student_id;

    private String student_name;

    private String start_time;

    private String image_url;

    private String address;
}
