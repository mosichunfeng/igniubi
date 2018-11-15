package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.UserAnswerHistoryDO;
import cn.neusoft.xuxiao.dao.entity.UserInfo;
import cn.neusoft.xuxiao.dao.entity.UserInfoAndBaseDO;
import cn.neusoft.xuxiao.service.inf.IUserService;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.AdminLoginResult;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.BindStudentInfoResponse;
import cn.neusoft.xuxiao.webapi.entity.BindUserInfoRequest;
import cn.neusoft.xuxiao.webapi.entity.EnsureJoinResponse;
import cn.neusoft.xuxiao.webapi.entity.GetSessionKeyAndOpenIdResponse;
import cn.neusoft.xuxiao.webapi.entity.QueryUserAnserHistoryRequest;
import cn.neusoft.xuxiao.webapi.entity.StartAnswerQuestionResponse;
import cn.neusoft.xuxiao.webapi.entity.SubmitContentRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController extends BaseController {

	@Resource(name = "iUserServiceImpl")
	private IUserService userService;

	@RequestMapping("/user/getSessionKeyAndOropenid")
	@ResponseBody
	public String getSessionKeyAndOropenid(String code) {
		GetSessionKeyAndOpenIdResponse result = this.userService.getSessionKeyAndOropenid(code);
		return generateResponse(result, ServiceResponseCode.OK);
	}

	@RequestMapping("/user/getWxCountInfo")
	@ResponseBody
	public String getWxCountInfo(BindUserInfoRequest reqMsg) {
		UserInfo result = this.userService.bindUserInfo(reqMsg);
		return createResponse(result, ServiceResponseCode.OK);
	}

	@RequestMapping("/user/bindUserInfo")
	@ResponseBody
	public String bindUserInfo(BindStudentInfoRequest reqMsg) {
		BindStudentInfoResponse result = this.userService.bindStudentInfo(reqMsg);
		return createResponse(result, ServiceResponseCode.OK);
	}
	
	@ResponseBody
	@RequestMapping("/user/getUserInfo" )
	public String getUserInfo(String id) {
		UserInfo result = userService.getUserInfo(id);
		return createResponse(result, ServiceResponseCode.OK);
	}

	@ResponseBody
	@RequestMapping("/user/getUserInfoAndBase")
	public String getUserInfoAndBase(String id){
		UserInfoAndBaseDO result = userService.getUserInfoAndBase(id);
		return createResponse(result, ServiceResponseCode.OK);
	}
	
	@ResponseBody
	@RequestMapping("/user/ensureJoin")
	public String ensureJoin(String user_id,String base_id){
		EnsureJoinResponse result = userService.ensureJoin(user_id, base_id);
		return createResponse(result, ServiceResponseCode.OK);
	}

	@RequestMapping("/user/startAnswerQuestion")
	@ResponseBody
	public String startAnswerQuestion(String user_id,String code) {
		StartAnswerQuestionResponse result = userService.startAnswerQuestion(user_id, code);
		return createResponse(result, ServiceResponseCode.OK);
	}

	@RequestMapping("/user/submitContent")
	@ResponseBody
	public String submitContent(SubmitContentRequest reqMsg) {
		this.userService.submitContent(reqMsg);
		return null;
	}

	@RequestMapping("/user/getAnswerHistory")
	@ResponseBody
	public String getAnswerHistory(QueryUserAnserHistoryRequest reqMsg) {
		List<UserAnswerHistoryDO> result = this.userService.getAnswerHistory(reqMsg);
		return createResponse(result, 200);
	}

	@RequestMapping("/user/adminLogin")
	public String adminLogin(String username, String password,ModelMap map) {
		AdminLoginResult result = this.userService.adminLogin(username, password);
		map.put("result", result);
		return "success";
	}
	
	@RequestMapping("/user/savePhoto")
	public void savePhoto(HttpServletRequest request,@RequestParam("proxyfile") MultipartFile file,String id) throws IOException{
		String preffix = request.getServletContext().getRealPath("/");
		String path = preffix+StringUtil.getUUid()+"_"+id+".jpg";
		System.out.println(path);
		File newFile = new File(path);

		file.transferTo(newFile);
	}
}