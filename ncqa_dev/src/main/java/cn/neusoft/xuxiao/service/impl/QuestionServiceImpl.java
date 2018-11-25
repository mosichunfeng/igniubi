package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.dao.inf.IQuestionDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IQuestionService;
import cn.neusoft.xuxiao.utils.ExcelUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.neusoft.xuxiao.utils.ValidationUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("iQuesionService")
public class QuestionServiceImpl implements IQuestionService {

	@Resource(name = "IQuestionDao")
	private IQuestionDao questionDao;

	@Transactional
	public void insertDataToBase(MultipartFile file,String base_id) {
		String fileName = file.getOriginalFilename();
		String[] columns = { "问题编号", "问题", "选项类型", "选项A", "选项B", "选项C", "选项D", "正确答案" };
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map<String, String>> list = ExcelUtil.parseExcel(fileName, is, columns);

		List<Question> questionList = new ArrayList<Question>();
		List<Answer> answerList = new ArrayList<Answer>();
		List<RightAnswer> rightAnswerList = new ArrayList<RightAnswer>();
		Question question = null;
		for (Map<String, String> map : list) {
			question = new Question();
			question.setQuestion_base_id(Integer.valueOf(base_id));
			question.setQuestion_index(Integer.valueOf((String) map.get("问题编号")).intValue());
			question.setContent((String) map.get("问题"));
			question.setSelect_type(Integer.valueOf((String) map.get("选项类型")).intValue());

			Answer answer = new Answer();
			answer.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
			answer.setAnswer_index("选项A");
			answer.setAnswer_content((String) map.get("选项A"));

			Answer answer1 = new Answer();
			answer1.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
			answer1.setAnswer_index("选项B");
			answer1.setAnswer_content((String) map.get("选项B"));

			Answer answer2 = new Answer();
			answer2.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
			answer2.setAnswer_index("选项C");
			answer2.setAnswer_content((String) map.get("选项C"));

			Answer answer3 = new Answer();
			answer3.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
			answer3.setAnswer_index("选项D");
			answer3.setAnswer_content((String) map.get("选项D"));

			RightAnswer rightAnswer = new RightAnswer();
			rightAnswer.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
			rightAnswer.setAnswer_index((String) map.get("正确答案"));
			rightAnswer.setAnswer_content((String) map.get("选项" + (String) map.get("正确答案")));

			questionList.add(question);
			answerList.add(answer);
			answerList.add(answer1);
			answerList.add(answer2);
			answerList.add(answer3);
			rightAnswerList.add(rightAnswer);
		}

		questionDao.insertQuestion(questionList);
		for (Question question1 : questionList) {
			for (Answer answer : answerList) {
				if (answer.getQuestion_id() == question1.getQuestion_index())
					answer.setQuestion_id(question1.getId());
			}
			for(RightAnswer right : rightAnswerList){
				if(right.getQuestion_id() == question1.getQuestion_index())
					right.setQuestion_id(question1.getId());
			}
		}
		this.questionDao.insertAnswer(answerList);
		this.questionDao.insertRightAnswer(rightAnswerList);
	}

	@Override
	public List<QuestionBase> getAllQuestionBase() {
		return questionDao.getAllQuestionBase();
	}

	@Override
	public List<Question> getAllQuestionByBaseId(int base_id) {
		 List<Question> questionList = questionDao.findQuestionListByBaseId(base_id);
		 
		 for(Question question : questionList){
			 List<Answer> answerList = questionDao.findAnswerListByQuestionId(question.getId());
			 question.setAnswer_list(answerList);
		 }
		 return questionList;
	}

	@Override
	public void parseExcel(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String[] columns = { "学号", "姓名", "性别" ,"联系电话"};
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map<String, String>> list = ExcelUtil.parseExcel(fileName, is, columns);

	}

	@Override
	public void exportBase(String base_id , HttpServletResponse response) {
		ValidationUtils.checkNotEmpty(base_id,"题库id不能为空");
		int baseId = Integer.valueOf(base_id);

		QuestionBase questionBase = questionDao.getQuestionBaseById(baseId);
		List<GradeDO> gradeList = questionDao.findGradeListByBaseId(baseId);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("成绩表");
		String[] headers = {"学号","姓名","班级","分数","答题开始时间","答题结束时间"};
		String fileName = questionBase.getName()+System.currentTimeMillis()+".xls";
		try {
			fileName = new String(fileName.getBytes("GB2312"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int rowNum = 1;
		HSSFRow row = sheet.createRow(0);
		for(int i=0;i<headers.length;i++){
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		for (int i = 0;i<gradeList.size();i++) {
			GradeDO grade = gradeList.get(i);
			HSSFRow row1 = sheet.createRow(rowNum);
			row1.createCell(0).setCellValue(grade.getStudent_id());
			row1.createCell(1).setCellValue(grade.getStudent_name());
			row1.createCell(2).setCellValue(grade.getStudent_class());
			row1.createCell(3).setCellValue(grade.getGrade());
			row1.createCell(4).setCellValue(grade.getStart_time());
			row1.createCell(5).setCellValue(grade.getEnd_time());
			rowNum++;
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		try {
			response.flushBuffer();
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION),"服务器异常，请联系管理员！");
		}


	}

}