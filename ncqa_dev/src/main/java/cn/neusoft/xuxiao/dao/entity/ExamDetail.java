package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class ExamDetail {

    private int question_base_id;
    private int question_base_name;

    private List<GradeDO> grade;

}
