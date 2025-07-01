package com.kayce.question_service.controller;
import com.kayce.question_service.model.Question;
import com.kayce.question_service.model.QuestionWrapper;
import com.kayce.question_service.model.Response;
import com.kayce.question_service.service.QuestionService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// RestController 用来处理HTTP请求
// 它是一个组合注解，包含了@Controller和@ResponseBody
@RestController
// RequestMapping 用来定义请求的基础路径
// eg. 当访问 /questions 时，会调用这个控制器中的方法
@RequestMapping("question")
public class QuestionController {

    // @Autowird 等价于 questionService = new QuestionService();
    // 它会自动注入QuestionService的实例
    @Autowired
    QuestionService questionService;

    @Autowired
    // see port
    Environment environment;

    @GetMapping("allQuestions")
    // ResponseEntity 用来封装HTTP响应
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // 通过路径变量获取 category 的值
    @GetMapping("category/{category}")
    // @PathVariable 用来获取路径中的变量
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    // PostMapping 用来处理POST请求
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // 被QuizService调用生成随机题目id 返回List<qId>
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, 
                                                             @RequestParam Integer numQ) {
        return questionService.getQuestionsForQuiz(category, numQ);
    }

    // 通过Id列表获取题目, quiz 服务通过id获取question信息(不包含answer)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        System.out.print("current port: "+ environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    // 计算Quiz得分
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
