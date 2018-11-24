package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.Register;
import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import cn.neusoft.xuxiao.dao.entity.UserInfoAndBaseDO;
import cn.neusoft.xuxiao.webapi.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract interface IUserService{
  public abstract GetSessionKeyAndOpenIdResponse getSessionKeyAndOropenid(String code);

  public  BindStudentInfoResponse bindStudentInfo(BindStudentInfoRequest reqMsg);

  public  UserInfo bindUserInfo(BindUserInfoRequest reqMsg);

  public  UserInfo getUserInfo(String id);
  
  public  UserInfoAndBaseDO getUserInfoAndBase(String id);

  public  List<UserAnswerHistoryDO> getAnswerHistory(QueryUserAnserHistoryRequest reqMsg);

  public SubmitContentResponse submitContent(SubmitContentRequest reqMsg);

  public  AdminLoginResult adminLogin(String username, String password);
  
  public  EnsureJoinResponse ensureJoin(String user_id,String base_id);

  public  StartAnswerQuestionResponse startAnswerQuestion(String user_id, String code);

  public IsRegisterResponse isRegister(String user_id);

  public IsRegisterResponse register(RegisterRequest reqMsg, MultipartFile file);
}