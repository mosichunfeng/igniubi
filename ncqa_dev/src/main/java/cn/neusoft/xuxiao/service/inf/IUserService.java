package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import cn.neusoft.xuxiao.dao.entity.UserInfoAndBaseDO;
import cn.neusoft.xuxiao.webapi.entity.AdminLoginResult;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoResponse;
import cn.neusoft.xuxiao.webapi.entity.BindUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.GetSessionKeyAndOpenIdResponse;
import cn.neusoft.xuxiao.webapi.entity.QueryUserAnserHistoryRequest;
import cn.neusoft.xuxiao.webapi.entity.SubmitContentRequest;
import java.util.List;

public abstract interface IUserService{
  public abstract GetSessionKeyAndOpenIdResponse getSessionKeyAndOropenid(String code);

  public  BindStudentInfoResponse bindStudentInfo(BindStudentInfoRequest reqMsg);

  public  UserInfo bindUserInfo(BindUserInfoRequest reqMsg);

  public  UserInfo getUserInfo(String id);
  
  public  UserInfoAndBaseDO getUserInfoAndBase(String id);

  public  List<UserAnswerHistoryDO> getAnswerHistory(QueryUserAnserHistoryRequest reqMsg);

  public  void submitContent(SubmitContentRequest reqMsg);

  public  AdminLoginResult adminLogin(String username, String password);
}