package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.service.inf.IStudentService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StudentController {

	@Resource(name = "iStudentServiceImpl")
	private IStudentService studentService;

	@RequestMapping({ "/import" })
	public String importStudentFromExcel(HttpServletRequest request, @RequestParam("proxyfile") MultipartFile file) {
		this.studentService.parseExcel(file);
		return "ok";
	}
	
}