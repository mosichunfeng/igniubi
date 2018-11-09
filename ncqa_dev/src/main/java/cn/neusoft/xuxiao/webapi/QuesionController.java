package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.service.inf.IQuestionService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class QuesionController
{

  @Resource(name="iQuesionService")
  private IQuestionService questionService;

  @RequestMapping({"/admin/upload"})
  public String upload(HttpServletRequest request, @RequestParam("proxyfile") MultipartFile file)
    throws Exception
  {
    this.questionService.insertDataToBase(file);

    return null;
  }

  @RequestMapping({"admin/getQuestionBaseHistory"})
  public String getQuestionBaseHistory()
  {
    return null;
  }

  @RequestMapping({"/admin/getQustionTestDetailById"})
  public String getQustionTestDetailById()
  {
    return null;
  }

  @RequestMapping({"/admin/exportExcel"})
  public String exportExcel()
  {
    return null;
  }
}