package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import cn.neusoft.xuxiao.service.inf.IQuestionService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class QuesionController {

	@Resource(name = "iQuesionService")
	private IQuestionService questionService;

	@RequestMapping("/admin/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, @RequestParam("proxyfile") MultipartFile file) throws Exception {
		this.questionService.insertDataToBase(file);

		return null;
	}

	@RequestMapping("admin/getQuestionBaseHistory")
	@ResponseBody
	public String getQuestionBaseHistory() {
		return null;
	}

	@RequestMapping("/admin/getQustionTestDetailById")
	@ResponseBody
	public String getQustionTestDetailById() {
		return null;
	}

	@RequestMapping("/admin/exportExcel")
	public String exportExcel() {
		return null;
	}
	
	@RequestMapping("/admin/getAllQuestionBase")
	public String getAllQuestionBase(ModelMap map){
		List<QuestionBase> result = questionService.getAllQuestionBase();
		map.put("result",result);
		return "success";
	}
	
	@RequestMapping("/admin/getQuestionByBaseId")
	public String getAllQuestionByBaseId(int base_id){
		return null;
	}
}