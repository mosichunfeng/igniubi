package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class GetSessionKeyAndOpenIdResponse extends BaseResponse {
	private int id;
	private boolean bind_info;
}