package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class EnsureJoinResponse extends BaseResponse{

	private String activity_code;
	private boolean isJoined;
}
