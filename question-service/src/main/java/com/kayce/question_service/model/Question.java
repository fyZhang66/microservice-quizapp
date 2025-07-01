package com.kayce.question_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// Model class for Question
// Entity 用来标识这个类是一个实体类
// 它会映射到数据库中的一张表
// @Data 会自动为所有的字段, eg, id, questionTitle, 生成getter和setter方法
@Data
@Entity
public class Question {
    // Id 作为主键
    // GeneratedValue 用来自动生成主键的值
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String category;
    private String difficultyLevel;
}
