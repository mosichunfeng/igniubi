package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class AdminLoginResult extends BaseResponse{
	private int id;
	private String username;
	private String password;
}