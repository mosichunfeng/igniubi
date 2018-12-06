package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class StudentRegister {
    private String student_id;
    private String student_name;
    private String student_class;
    private String register_info;
    private String register_time;
    private String register_address;
}
