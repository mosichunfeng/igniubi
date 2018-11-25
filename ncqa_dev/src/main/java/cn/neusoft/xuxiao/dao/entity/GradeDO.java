package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class GradeDO {

    private String student_id;

    private String student_name;

    private String student_class;

    private int grade;

    private String start_time;

    private String end_time;

}
