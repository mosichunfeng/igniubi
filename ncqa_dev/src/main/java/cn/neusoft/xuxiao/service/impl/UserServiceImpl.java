package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.Admin;
import cn.neusoft.xuxiao.dao.entity.StudentDO;
import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import cn.neusoft.xuxiao.dao.inf.IUserDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IUserService;
import cn.neusoft.xuxiao.utils.Base64Utils;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import cn.neusoft.xuxiao.utils.WxApplicationDecoder;
import cn.neusoft.xuxiao.webapi.entity.AdminLoginResult;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoResponse;
import cn.neusoft.xuxiao.webapi.entity.BindUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.GetSessionKeyAndOpenIdResponse;
import cn.neusoft.xuxiao.webapi.entity.QueryUserAnserHistoryRequest;
import cn.neusoft.xuxiao.webapi.entity.QueryUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.SubmitContentRequest;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("iUserServiceImpl")
public class UserServiceImpl
  implements IUserService
{

  @Resource(name="IUserDao")
  private IUserDao userDao;

  @Transactional
  public GetSessionKeyAndOpenIdResponse getSessionKeyAndOropenid(String code)
  {
    if (StringUtil.isEmpty(code)) {
      throw new BusinessException(String.valueOf(409), "登录code不能为空");
    }
    String[] ret = WxApplicationDecoder.parse(code);
    ValidationUtils.checkNotEmpty(ret[0], "code码非法");
    String openId = Base64Utils.encoder(ret[0]);
    UserInfo userInfo = this.userDao.findUserByOpenId(openId);
    boolean isBind = false;
    int key;
    if (null == userInfo) {
      key = this.userDao.insertUserByOpenId(openId).intValue();
    } else {
      System.out.println(userInfo.getStudent_id());
      key = userInfo.getId();
      if (userInfo.getStudent_id() != null) {
        isBind = true;
      }
    }
    GetSessionKeyAndOpenIdResponse result = new GetSessionKeyAndOpenIdResponse();
    result.setId(key);
    result.setBind_info(isBind);
    return result;
  }

  public BindStudentInfoResponse bindStudentInfo(BindStudentInfoRequest reqMsg)
  {
    ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getId()), "用户id不能为空");
    ValidationUtils.checkNotEmpty(reqMsg.getStudent_id(), "学号不能为空");
    ValidationUtils.checkNotEmpty(reqMsg.getStudent_name(), "姓名不能为空");
    StudentDO student = this.userDao.findStudentById(reqMsg.getStudent_id());

    if (!reqMsg.getStudent_name().equals(student.getStudent_name())) {
      throw new BusinessException(String.valueOf(409), "学号姓名不匹配");
    }
    this.userDao.bindStudentInfo(reqMsg.getId(), reqMsg.getStudent_id());
    BindStudentInfoResponse result = new BindStudentInfoResponse();
    result.setId(reqMsg.getId());
    result.setStudent_id(reqMsg.getStudent_id());
    result.setStudent_name(reqMsg.getStudent_name());
    return result;
  }

  public UserInfo bindUserInfo(BindUserInfoRequest reqMsg)
  {
    ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getId()), "用户id不能为空");
    UserInfo userInfo = new UserInfo();
    userInfo.setRegion(reqMsg.getCity());
    userInfo.setId(reqMsg.getId());
    userInfo.setSex(reqMsg.getGender() == 0 ? "男" : "女");
    userInfo.setUsername(reqMsg.getNick_name());
    userInfo.setHead_url(reqMsg.getAvatarUrl());
    this.userDao.updateUserInfoById(userInfo);
    return this.userDao.findUserById(userInfo.getId());
  }

  public UserInfo getUserInfo(QueryUserInfoRequest reqMsg)
  {
    return null;
  }

  public List<UserAnswerHistoryDO> getAnswerHistory(QueryUserAnserHistoryRequest reqMsg)
  {
    ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "用户id不能为空");
    return this.userDao.getAnswerHistory(reqMsg.getUser_id());
  }

  public void submitContent(SubmitContentRequest reqMsg)
  {
    ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "用户id不能为空");
    ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "题库id不能为空");

    int count = this.userDao.getQuestionCountByBaseId(reqMsg.getQuestion_base_id());
    if (count != reqMsg.getMap().size())
      throw new BusinessException(String.valueOf(409), "答案个数不匹配");
  }

  public AdminLoginResult adminLogin(String username, String password)
  {
    ValidationUtils.checkNotEmpty(username, "用户名不能为空");
    ValidationUtils.checkNotEmpty(password, "密码不能为空");
    AdminLoginResult result = new AdminLoginResult();
    Admin orgin = this.userDao.getAdminByUsername(username);
    ValidationUtils.checkNotEmpty(orgin, "无此用户");
    if (!orgin.getPassword().equals(password)) {
      throw new BusinessException(String.valueOf(402), "密码错误");
    }
    result.setId(orgin.getId());
    result.setUsername(orgin.getUsername());
    result.setPassword(orgin.getPassword());
    return result;
  }
}