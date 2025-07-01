package com.kayce.quiz_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kayce.quiz_service.dao.QuizDAO;
import com.kayce.quiz_service.feign.QuizInterface;
import com.kayce.quiz_service.model.Quiz;
import com.kayce.quiz_service.model.QuestionWrapper;
import com.kayce.quiz_service.model.Response;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDAO quizDAO;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // 通过quizInterface.getQuestionsForQuiz间接调用question service 的 /generate接口.
        // 得到题目id列表
        List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizDAO.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        // 通过Quiz的id获取题目id列表
        List<Integer> questionIds = quizDAO.findById(id).get().getQuestionIds();
        // 通过questionIds获取题目内容
        ResponseEntity<List<QuestionWrapper>> questionForUser = quizInterface.getQuestionsFromId(questionIds);
        return new ResponseEntity<>(questionForUser.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(List<Response> response) {
        ResponseEntity<Integer> score = quizInterface.getScore(response);
        return new ResponseEntity<>(score.getBody(), HttpStatus.OK);
    }
}
