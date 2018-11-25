package cn.neusoft.xuxiao.service.inf;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;

import javax.servlet.http.HttpServletResponse;

public abstract interface IQuestionService {
    public abstract void insertDataToBase(MultipartFile paramMultipartFile,String base_id);

    public abstract List<QuestionBase> getAllQuestionBase();

    public abstract List<Question> getAllQuestionByBaseId(int base_id);

    public abstract void parseExcel(MultipartFile paramMultipartFile);

    public abstract void exportBase(String base_id, HttpServletResponse response);
}