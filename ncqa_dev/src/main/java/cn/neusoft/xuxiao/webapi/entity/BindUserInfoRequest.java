package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class BindUserInfoRequest {
	private int id;
	private String avatarUrl;
	private String nick_name;
	private String city;
	private String country;
	private int gender;
}