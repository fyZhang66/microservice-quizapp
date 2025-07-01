package com.kayce.quiz_service.model;

import lombok.Data;

// QuestionWrapper 包含了问题的标题和四个选项, 不返回答案
// 用于给getQuizQuestions方法返回数据
@Data
public class QuestionWrapper {

    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(Integer id, String questionTitle, String option1, String option2, String option3,
            String option4) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
