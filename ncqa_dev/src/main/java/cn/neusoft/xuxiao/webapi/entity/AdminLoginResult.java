package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class AdminLoginResult {
	private int id;
	private String username;
	private String password;
}