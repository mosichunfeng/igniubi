package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.Answer;
import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import cn.neusoft.xuxiao.dao.entity.RightAnswer;
import cn.neusoft.xuxiao.dao.inf.IQuestionDao;
import cn.neusoft.xuxiao.service.inf.IQuestionService;
import cn.neusoft.xuxiao.utils.ExcelUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("iQuesionService")
public class QuestionServiceImpl implements IQuestionService {

	@Resource(name = "IQuestionDao")
	private IQuestionDao questionDao;

	@Transactional
	public void insertDataToBase(MultipartFile file) {
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
			question.setQuestion_base_id(1);
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
}