package com.kayce.quiz_service.controller;
import com.kayce.quiz_service.model.QuestionWrapper;
import com.kayce.quiz_service.model.QuizDto;
import com.kayce.quiz_service.model.Response;
import com.kayce.quiz_service.service.QuizService;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    // /quiz/create?category=Java&numQ=10&title=Java Basics
    // @RequestParam 用来获取请求参数
    // ⬇️
    // 改成@RequestBody的形式 Dto(Data Transfer Object) 用来接收前端传来的数据
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategoryName(), 
                                         quizDto.getNumQuestions(), 
                                         quizDto.getTitle());
    } 

    // 获取指定ID的Quiz题目
    // /quiz/get/{id}
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    // 提交Quiz答案, 返回分数
    // Response 是一个自定义的类，用来封装用户的答案
    @PostMapping("submit")
    public ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> response) {
        return quizService.calculateResult(response);
    }
}
