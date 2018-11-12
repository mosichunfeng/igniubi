package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ActivityCodeConstants;
import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.ActivityCodeDO;
import cn.neusoft.xuxiao.dao.entity.Admin;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import cn.neusoft.xuxiao.dao.entity.StudentDO;
import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import cn.neusoft.xuxiao.dao.entity.UserInfoAndBaseDO;
import cn.neusoft.xuxiao.dao.inf.IQuestionDao;
import cn.neusoft.xuxiao.dao.inf.IUserDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IUserService;
import cn.neusoft.xuxiao.utils.Base64Utils;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.utils.TimeTool;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import cn.neusoft.xuxiao.utils.WxApplicationDecoder;
import cn.neusoft.xuxiao.webapi.entity.AdminLoginResult;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoResponse;
import cn.neusoft.xuxiao.webapi.entity.BindUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.EnsureJoinResponse;
import cn.neusoft.xuxiao.webapi.entity.GetSessionKeyAndOpenIdResponse;
import cn.neusoft.xuxiao.webapi.entity.QueryUserAnserHistoryRequest;
import cn.neusoft.xuxiao.webapi.entity.SubmitContentRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("iUserServiceImpl")
public class UserServiceImpl implements IUserService {

	@Resource(name = "IUserDao")
	private IUserDao userDao;

	@Resource(name = "IQuestionDao")
	private IQuestionDao questionDao;

	@Transactional
	public GetSessionKeyAndOpenIdResponse getSessionKeyAndOropenid(String code) {
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

	public BindStudentInfoResponse bindStudentInfo(BindStudentInfoRequest reqMsg) {
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

	public UserInfo bindUserInfo(BindUserInfoRequest reqMsg) {
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

	public UserInfo getUserInfo(String id) {
		ValidationUtils.checkNotEmpty(id, "用户id不能为空");
		UserInfo userInfo = userDao.findUserById(Integer.parseInt(id));
		if (userInfo == null) {
			throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "无此用户");
		}
		return userInfo;
	}

	public List<UserAnswerHistoryDO> getAnswerHistory(QueryUserAnserHistoryRequest reqMsg) {
		ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "用户id不能为空");
		return this.userDao.getAnswerHistory(reqMsg.getUser_id());
	}

	public void submitContent(SubmitContentRequest reqMsg) {
		ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "用户id不能为空");
		ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "题库id不能为空");

		int count = this.userDao.getQuestionCountByBaseId(reqMsg.getQuestion_base_id());
		if (count != reqMsg.getMap().size())
			throw new BusinessException(String.valueOf(409), "答案个数不匹配");
	}

	public AdminLoginResult adminLogin(String username, String password) {
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

	@Override
	public UserInfoAndBaseDO getUserInfoAndBase(String id) {
		ValidationUtils.checkNotEmpty(id, "用户id不能为空");
		StudentDO student = userDao.findStudentByUid(Integer.parseInt(id));
		if (student == null) {
			throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "无此学生");
		}
		UserInfoAndBaseDO result = new UserInfoAndBaseDO();
		result.setId(Integer.parseInt(id));
		result.setStudent_id(student.getStudent_id());
		result.setStudent_name(student.getStudent_name());

		List<QuestionBase> list = new ArrayList<QuestionBase>();

		List<QuestionBase> allQuestionBase = questionDao.getAllQuestionBase();
		if (allQuestionBase != null) {
			for (QuestionBase questionBase : allQuestionBase) {
				if (isAvalid(questionBase.getEnd_time())) {
					list.add(questionBase);
				}
			}
		}
		result.setBase(list);
		return result;
	}

	/**
	 * 计算时间差值
	 * 
	 * @return 相差小时数
	 * @date 2018年10月12日 下午4:08:07
	 * @author hepei
	 */
	private boolean isAvalid(String endtime) {
		if (StringUtil.isEmpty(endtime)) {
			return false;
		}
		boolean avalid = false;
		Date endDate = TimeTool.StrToDate(endtime);
		long interval = new Date().getTime() - endDate.getTime();
		if (interval < 0) {
			return true;
		}
		return avalid;
	}

	@Override
	public EnsureJoinResponse ensureJoin(String user_id, String base_id) {
		ValidationUtils.checkNotEmpty(user_id, "用户id不能为空");
		ValidationUtils.checkNotEmpty(base_id, "题库id不能为空");

		// 判断活动是否过期
		QuestionBase base = questionDao.getQuestionBaseById(Integer.parseInt(base_id));
		if (!isAvalid(base.getEnd_time())) {
			throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "活动已经结束！无法报名！");
		}
		
		ActivityCodeDO activityCode = questionDao.getActivityCode(Integer.parseInt(user_id), Integer.parseInt(base_id));
		
		EnsureJoinResponse response = new EnsureJoinResponse();
		if (activityCode == null) {
			String code = user_id + ActivityCodeConstants.MIDSINGNAL
					+ base_id;
			ActivityCodeDO activity = new ActivityCodeDO();
			activity.setUser_id(Integer.valueOf(user_id));
			activity.setQuestion_base_id(Integer.valueOf(base_id));
			activity.setCode(code);
			questionDao.insertActivityCode(activity);
			response.setActivity_code(code);
			response.setJoined(false);
		}else{
			response.setActivity_code(activityCode.getCode());
			response.setJoined(true);
		}
		return response;
	}
}