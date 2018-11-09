package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import cn.neusoft.xuxiao.webapi.entity.AdminLoginResult;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoResponse;
import cn.neusoft.xuxiao.webapi.entity.BindUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.GetSessionKeyAndOpenIdResponse;
import cn.neusoft.xuxiao.webapi.entity.QueryUserAnserHistoryRequest;
import cn.neusoft.xuxiao.webapi.entity.QueryUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.SubmitContentRequest;
import java.util.List;

public abstract interface IUserService
{
  public abstract GetSessionKeyAndOpenIdResponse getSessionKeyAndOropenid(String paramString);

  public abstract BindStudentInfoResponse bindStudentInfo(BindStudentInfoRequest paramBindStudentInfoRequest);

  public abstract UserInfo bindUserInfo(BindUserInfoRequest paramBindUserInfoRequest);

  public abstract UserInfo getUserInfo(QueryUserInfoRequest paramQueryUserInfoRequest);

  public abstract List<UserAnswerHistoryDO> getAnswerHistory(QueryUserAnserHistoryRequest paramQueryUserAnserHistoryRequest);

  public abstract void submitContent(SubmitContentRequest paramSubmitContentRequest);

  public abstract AdminLoginResult adminLogin(String paramString1, String paramString2);
}