package com.kayce.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kayce.quiz_service.model.QuestionWrapper;
import com.kayce.quiz_service.model.Response;

 
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    // 被QuizService调用生成随机题目id 返回List<qId>
    // question是一级服务, quiz是二级服务
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, 
                                                             @RequestParam Integer numQ);

    // 通过Id列表获取题目, quiz 服务通过id获取question信息(不包含answer)
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    // 计算Quiz得分
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
