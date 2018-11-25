package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import cn.neusoft.xuxiao.service.inf.IQuestionService;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
    public String upload(@RequestParam("proxyfile") MultipartFile file,String base_id) throws Exception {
        this.questionService.insertDataToBase(file,base_id);
        return null;
    }

    @RequestMapping("/admin/uploadPage")
    public String uploadPage(int base_id, String base_name, ModelMap map) {
        map.put("base_id", base_id);
        map.put("base_name", base_name);
        return "upload";
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
    public String getAllQuestionBase(ModelMap map) {
        List<QuestionBase> result = questionService.getAllQuestionBase();
        map.put("result", result);
        return "success";
    }

    @RequestMapping("/admin/getQuestionByBaseId")
    public String getAllQuestionByBaseId(int base_id, ModelMap map) {
        List<Question> result = questionService.getAllQuestionByBaseId(base_id);
        map.put("result", result);
        return "question";
    }


    @RequestMapping("admin/exportBase")
    public void exportBase(String base_id, HttpServletResponse response) throws IOException {
       questionService.exportBase(base_id, response);
    }
}