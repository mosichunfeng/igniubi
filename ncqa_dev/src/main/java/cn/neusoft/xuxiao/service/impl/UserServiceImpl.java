package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ActivityCodeConstants;
import cn.neusoft.xuxiao.constants.ExamConstants;
import cn.neusoft.xuxiao.constants.ServerConstants;
import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.dao.inf.IQuestionDao;
import cn.neusoft.xuxiao.dao.inf.IUserDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IUserService;
import cn.neusoft.xuxiao.utils.*;
import cn.neusoft.xuxiao.webapi.entity.*;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
        UserInfo info = new UserInfo();
        info.setOpen_id(openId);
        if (null == userInfo) {
            userDao.insertUserByOpenId(info).intValue();
            key = info.getId();
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

    @Transactional
    public BindStudentInfoResponse bindStudentInfo(BindStudentInfoRequest reqMsg) {
        ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getId()), "用户id不能为空");
        ValidationUtils.checkNotEmpty(reqMsg.getStudent_id(), "学号不能为空");
        ValidationUtils.checkNotEmpty(reqMsg.getStudent_name(), "姓名不能为空");

        UserInfo user = userDao.findUserByStudentId(reqMsg.getStudent_id());


        if (user != null) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "该学生已被其他设备绑定！");
        }

        StudentDO student = this.userDao.findStudentById(reqMsg.getStudent_id());
        if (null == student) {
            throw new BusinessException(String.valueOf(410), "学号输入错误！");
        }
        if (!reqMsg.getStudent_name().equals(student.getStudent_name())) {
            throw new BusinessException(String.valueOf(409), "学号姓名不匹配");
        }
        this.userDao.bindStudentInfo(reqMsg.getId(), reqMsg.getStudent_id());
        BindStudentInfoResponse result = new BindStudentInfoResponse();
        result.setId(reqMsg.getId());
        result.setStudent_id(reqMsg.getStudent_id());
        result.setStudent_name(reqMsg.getStudent_name());
        result.setStudent_gender(student.getStudent_gender());
        return result;
    }

    @Transactional
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

    @Transactional
    public UserInfo getUserInfo(String id) {
        ValidationUtils.checkNotEmpty(id, "用户id不能为空");
        UserInfo userInfo = userDao.findUserById(Integer.parseInt(id));
        if (userInfo == null) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "无此用户");
        }
        return userInfo;
    }

    public List<ExamDO> getAnswerHistory(QueryUserAnserHistoryRequest reqMsg) {
        ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "用户id不能为空");
        return userDao.getAnswerHistory(reqMsg.getUser_id());
    }

    public SubmitContentResponse submitContent(SubmitContentRequest reqMsg) {
        ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getUser_id()), "用户id不能为空");
        ValidationUtils.checkNotEmpty(Integer.valueOf(reqMsg.getQuestion_base_id()), "题库id不能为空");

        int uid = Integer.valueOf(reqMsg.getUser_id());
        int baseId = Integer.valueOf(reqMsg.getQuestion_base_id());
        String code = uid + ActivityCodeConstants.MIDSINGNAL + baseId;

        if (ExamConstants.QUESTION_COUNT != reqMsg.getDataMap().size())
            throw new BusinessException(String.valueOf(409), "答案个数不匹配");


        //验证答题完成
        ExamDO examDO = questionDao.findExamHistoryByCode(code);
        if (examDO == null) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "去你吗的，没事做搬砖去！");
        }

        ExamDO change = new ExamDO();
        change.setActivity_code(code);
        change.setEnd_time(TimeTool.DateToString(new Date()));
        change.setStatus(ExamConstants.FINISHED);

        //计算答题时间
        long minutes = caculateMinutes(examDO.getStart_time());
        //超时，判0
        if (minutes > ExamConstants.ALL_EXAM_TIME) {
            examDO.setGrade(0);
        } else {
            Map<Integer, String> orgin = new HashMap<>();

            List<Question> questionList = questionDao.findQuestionListByBaseId(baseId);
            for (Question question : questionList) {
                RightAnswer rightAnswer = questionDao.findRightAnswerByQuestionId(question.getId());
                orgin.put(question.getId(), rightAnswer.getAnswer_index());
            }
            int grade = caculateGrade(orgin, reqMsg.getDataMap());
            change.setGrade(grade);
        }
        questionDao.updateGradeByCode(change);

        SubmitContentResponse response = new SubmitContentResponse();
        response.setGrade(change.getGrade());
        return response;
    }

    private int caculateGrade(Map<Integer, String> orgin, Map<Integer, String> userData) {

        Set<Integer> i = orgin.keySet();

        int grade = 0;
        Set<Integer> index = userData.keySet();
        for (Integer one : index) {
            String right_answer = orgin.get(one);
            String user_answer = userData.get(one);
            System.out.print("用户答案是" + user_answer);
            System.out.print("正确答案是" + right_answer);
            if (right_answer.equals(user_answer)) {
                grade += ExamConstants.ONE_SELECT_GRADE;
            }
            System.out.println();
        }
        return grade;
    }

    @Transactional
    public AdminLoginResult adminLogin(String username, String password) {
        ValidationUtils.checkNotEmpty(username, "用户名不能为空");
        ValidationUtils.checkNotEmpty(password, "密码不能为空");
        AdminLoginResult result = new AdminLoginResult();
        Admin orgin = this.userDao.getAdminByUsername(username);
        if (null == orgin) {
            result.setCode(ServiceResponseCode.NO_THIS_USER);
            result.setRemark("无此用户！");
        } else {
            if (!orgin.getPassword().equals(password)) {
                result.setCode(ServiceResponseCode.PASSWORD_ERROR);
                result.setRemark("密码错误！");
            } else {
                result.setCode(ServiceResponseCode.OK);
                result.setUsername(orgin.getUsername());
                result.setRemark("登录成功");
            }
        }
        return result;
    }

    @Override
    @Transactional
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
            avalid = true;
        }
        return avalid;
    }

    private long caculateMinutes(String time) {
        if (StringUtil.isEmpty(time)) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常！请联系管理员!");
        }
        Date endDate = TimeTool.StrToDate(time);
        long interval = new Date().getTime() - endDate.getTime();
        return interval / 60000;
    }

    @Override
    @Transactional
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
            activity.setTime(TimeTool.DateToString(new Date()));
            questionDao.insertActivityCode(activity);
            response.setActivity_code(code);
            response.setJoined(false);
        } else {
            response.setActivity_code(activityCode.getCode());
            response.setJoined(true);
        }
        return response;
    }

    @Override
    @Transactional
    public StartAnswerQuestionResponse startAnswerQuestion(String user_id, String code) {
        ValidationUtils.checkNotEmpty(user_id, "用户id不能为空");
        ValidationUtils.checkNotEmpty(code, "答题码不能为空");
        if (!code.contains(ActivityCodeConstants.MIDSINGNAL)) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "答题码格式错误");
        }
        String[] split = code.split(ActivityCodeConstants.MIDSINGNAL);
        if (!split[0].equals(user_id)) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "请不要帮别人答题！");
        }
        int uid = Integer.valueOf(user_id);
        int base_id = Integer.valueOf(split[1]);

        StartAnswerQuestionResponse response = new StartAnswerQuestionResponse();

        ExamDO examDO = questionDao.findExamHistoryByCode(code);
        QuestionBase qb = questionDao.getQuestionBaseById(base_id);
        List<Question> questionList = questionDao.findRandQuestionListByBaseId(base_id);

        qb.setQuestion(questionList);

        int index = 1;
        for (Question question : questionList) {
            question.setIndex(index);
            List<Answer> answerList = questionDao.findAnswerListByQuestionId(question.getId());
            question.setAnswer_list(answerList);
            index++;
        }

        //开始答题
        if (examDO == null) {
            examDO = new ExamDO();
            examDO.setUser_id(uid);
            examDO.setQuestion_base_id(Integer.valueOf(split[1]));
            examDO.setActivity_code(code);
            examDO.setStart_time(TimeTool.DateToString(new Date()));
            examDO.setStatus(ExamConstants.STARTED);
            questionDao.insertExamHistory(examDO);

            response.setUser_id(uid);
            response.setLast_minutes(ExamConstants.ALL_EXAM_TIME);
            response.setStatus(ExamConstants.STARTED);
            response.setQuestion_base(qb);
        } else {
            if (examDO.getStatus() == ExamConstants.FINISHED) {
                response.setUser_id(uid);
                response.setStatus(ExamConstants.FINISHED);
                response.setGrade(examDO.getGrade());
            } else {
                long minutes = caculateMinutes(examDO.getStart_time());
                response.setUser_id(uid);
                response.setLast_minutes(ExamConstants.ALL_EXAM_TIME - minutes);
                response.setStatus(ExamConstants.STARTED);
                response.setQuestion_base(qb);
            }
        }

        return response;
    }

    @Override
    public IsRegisterResponse isRegister(String user_id) {
        ValidationUtils.checkNotEmpty(user_id, "user_id不能为空");
        int uid = Integer.valueOf(user_id);
        StudentDO stu = userDao.findStudentByUid(uid);
        if (stu == null) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "无此用户");
        }

        IsRegisterResponse response = new IsRegisterResponse();

        Register register = userDao.findRecentRegisterByStudentId(stu.getStudent_id());
        if (register != null) {
            String registerTime = register.getStart_time();
            int weekOfYear = TimeTool.getWeekOfYear(TimeTool.StrToDate(registerTime));
            int weekOfYear1 = TimeTool.getWeekOfYear();
            if (weekOfYear == weekOfYear1) {

                /**
                 * 外出实习学生，一周签到一次
                 */
                if (stu.getWork_detail() == 1) {
                    response.setRegister(true);
                    response.setStart_time(register.getStart_time());
                    response.setStudent_name(register.getStudent_name());
                } else {
//                    if (DateUtils.isSameDay(TimeTool.StrToDate(register.getStart_time()), new Date())) {
//                        response.setRegister(true);
//                        response.setStart_time(register.getStart_time());
//                        response.setStudent_name(register.getStudent_name());
//                    } else {
//                        response.setStudent_name(stu.getStudent_name());
//                        response.setRegister(false);
//                    }
                    response.setStudent_name(stu.getStudent_name());
                    response.setRegister(false);
                }
            } else {
                response.setStudent_name(stu.getStudent_name());
                response.setRegister(false);
            }
        } else {
            response.setStudent_name(stu.getStudent_name());
            response.setRegister(false);
        }
        return response;
    }

    @Transactional
    public IsRegisterResponse register(RegisterRequest reqMsg, MultipartFile file) {

        ValidationUtils.checkNotEmpty(reqMsg.getUser_id(), "user_id不能为空!");
        ValidationUtils.checkNotEmpty(reqMsg.getAddress(), "地址非法！");
        Integer uid = Integer.valueOf(reqMsg.getUser_id());
        StudentDO student = userDao.findStudentByUid(uid);

        Register reg = userDao.findRecentRegisterByStudentId(student.getStudent_id());
        if (reg != null) {
            String start_time = reg.getStart_time();
            int weekOfYear = TimeTool.getWeekOfYear(TimeTool.StrToDate(start_time));
            int weekOfYear1 = TimeTool.getWeekOfYear();
            if (weekOfYear == weekOfYear1) {
                if (student.getWork_detail() == 1) {
                    System.out.println(student.getStudent_id() + student.getStudent_name());
                    throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "本周已经签到过了，请勿再次签到！");
                } else {
//                    if (DateUtils.isSameDay(TimeTool.StrToDate(reg.getStart_time()), new Date())) {
//                        System.out.println(student.getStudent_id() + student.getStudent_name());
//                        throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "今天已经签到过了，请勿再次签到！");
//                    }
                }
            }
        }

        Register register = new Register();
        register.setStudent_id(student.getStudent_id());
        register.setStudent_name(student.getStudent_name());
        register.setStart_time(TimeTool.DateToString(new Date()));
        register.setAddress(reqMsg.getAddress());

        userDao.register(register);
        String filePath = ServerConstants.REGISTER_IMAGES_ROOT_PATH + TimeTool.getWeekOfYear();

        File dir = new File(filePath);
        if (!dir.exists()) {
            boolean result = dir.mkdir();
            if (!result) {
                throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员!");
            }
        }

        File imageFile = new File(filePath + "/" + student.getStudent_id() + student.getStudent_name() + TimeTool.DateToString(new Date()) + ".jpg");
        try {
            file.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "签到服务器异常，请联系管理员!");
        }
        IsRegisterResponse response = new IsRegisterResponse();
        response.setStudent_name(student.getStudent_name());
        response.setRegister(true);
        response.setStart_time(TimeTool.DateToString(new Date()));

        return response;
    }

    @Override
    public SubmitFeedbackResponse submitFeedback(SubmitFeedbackRequest reqMsg) {
        ValidationUtils.checkNotEmpty(String.valueOf(reqMsg.getUser_id()), "user_id不能为空！");
        ValidationUtils.checkNotEmpty(reqMsg.getTitle(), "标题不能为空!");
        ValidationUtils.checkNotEmpty(reqMsg.getContent(), "内容不能为空!");

        Feedback feedback = new Feedback();
        feedback.setUser_id(reqMsg.getUser_id());
        feedback.setTitle(reqMsg.getTitle());
        feedback.setContent(reqMsg.getContent());
        feedback.setRecord_time(TimeTool.DateToString(new Date()));

        userDao.saveFeedback(feedback);
        return new SubmitFeedbackResponse();
    }

    @Override
    public void exportRegister(HttpServletResponse response) {
        List<StudentDO> allWGStudent = userDao.findAllWGStudent();

        List<StudentRegister> info = new ArrayList<>();
        RigsterChoose(allWGStudent, info);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("成绩表");
        String[] headers = {"学号", "姓名", "班级", "签到情况", "签到时间", "签到地点"};
        String fileName = "网工团队第" + TimeTool.getWeekOfYear() + "周签到情况表" + System.currentTimeMillis() + ".xls";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        cellFormat(info, sheet, rowNum);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员！");
        }

    }

    private void RigsterChoose(List<StudentDO> allWGStudent, List<StudentRegister> info) {
        for (StudentDO student : allWGStudent) {
            StudentRegister sr = new StudentRegister();
            sr.setStudent_id(student.getStudent_id());
            sr.setStudent_name(student.getStudent_name());
            sr.setStudent_class(student.getStudent_class());

            Register register = userDao.findRecentRegisterByStudentId(student.getStudent_id());

            if (register == null) {
                sr.setRegister_info("未签到");
            } else {
                String start_time = register.getStart_time();
                OutSchoolStudentChoose(sr, register, start_time);
            }
            info.add(sr);
        }
    }

    @Override
    public void exportTodayInRegister(HttpServletResponse response) {

        List<StudentDO> allWGStudent = userDao.findAllWGStudent();
        List<StudentRegister> info = new ArrayList<>();
        Iterator<StudentDO> iterator = allWGStudent.iterator();
        while (iterator.hasNext()) {
            StudentDO next = iterator.next();
            if (next.getWork_detail() == 1) {
                iterator.remove();
            }
        }

        for (StudentDO student : allWGStudent) {
            StudentRegister sr = new StudentRegister();
            sr.setStudent_id(student.getStudent_id());
            sr.setStudent_name(student.getStudent_name());
            sr.setStudent_class(student.getStudent_class());

            Register register = userDao.findRecentRegisterByStudentId(student.getStudent_id());
            if (register != null) {
                if (DateUtils.isSameDay(TimeTool.StrToDate(register.getStart_time()), new Date())) {
                    sr.setRegister_info("已签到");
                    sr.setRegister_time(register.getStart_time());
                    sr.setRegister_address(register.getAddress());
                } else {
                    sr.setRegister_info("未签到");
                }
            } else {
                sr.setRegister_info("未签到");
            }
            info.add(sr);
        }


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("成绩表");
        String[] headers = {"学号", "姓名", "班级", "签到情况", "签到时间", "签到地点"};
        String fileName = "网工团队第" + TimeTool.DateToShortString(new Date()) + "天在校学生签到情况表" + System.currentTimeMillis() + ".xls";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        cellFormat(info, sheet, rowNum);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员！");
        }
    }

    private void cellFormat(List<StudentRegister> info, HSSFSheet sheet, int rowNum) {
        for (int i = 0; i < info.size(); i++) {
            StudentRegister sr = info.get(i);
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(sr.getStudent_id());
            row1.createCell(1).setCellValue(sr.getStudent_name());
            row1.createCell(2).setCellValue(sr.getStudent_class());
            row1.createCell(3).setCellValue(sr.getRegister_info());
            row1.createCell(4).setCellValue(sr.getRegister_time());
            row1.createCell(5).setCellValue(sr.getRegister_address());
            rowNum++;
        }
    }

    @Override
    public void exportThisWeekOutRegister(HttpServletResponse response) {
        List<StudentDO> allWGStudent = userDao.findAllWGStudent();
        List<StudentRegister> info = new ArrayList<>();
        Iterator<StudentDO> iterator = allWGStudent.iterator();
        while (iterator.hasNext()) {
            StudentDO next = iterator.next();
            if (next.getWork_detail() == 0) {
                iterator.remove();
            }
        }

        RigsterChoose(allWGStudent, info);


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("成绩表");
        String[] headers = {"学号", "姓名", "班级", "签到情况", "签到时间", "签到地点"};
        String fileName = "网工团队第" + TimeTool.getWeekOfYear() + "周外出学生签到情况表" + System.currentTimeMillis() + ".xls";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        cellFormat(info, sheet, rowNum);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员！");
        }
    }

    private void OutSchoolStudentChoose(StudentRegister sr, Register register, String start_time) {
        if (!StringUtil.isEmpty(start_time)) {
            int weekOfYear = TimeTool.getWeekOfYear(TimeTool.StrToDate(start_time));
            int weekOfYear1 = TimeTool.getWeekOfYear();
            if (weekOfYear == weekOfYear1) {
                sr.setRegister_info("已签到");
                sr.setRegister_time(register.getStart_time());
                sr.setRegister_address(register.getAddress());
            } else {
                sr.setRegister_info("未签到");
            }
        }
    }

}