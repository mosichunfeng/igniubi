package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class IsRegisterResponse extends BaseResponse{
    private String student_name;
    private String start_time;
    private boolean isRegister;
}
