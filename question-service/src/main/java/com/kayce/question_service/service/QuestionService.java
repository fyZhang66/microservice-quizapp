package com.kayce.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.kayce.question_service.dao.QuestionDAO;
import com.kayce.question_service.model.Question;
import com.kayce.question_service.model.QuestionWrapper;
import com.kayce.question_service.model.Response;

//@Service 等价于 @Component
@Service
public class QuestionService {
    
    @Autowired
    QuestionDAO questionDAO;

    // ResponseEntity 用来封装HTTP响应
    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK); 
        }catch(Exception e){
            e.printStackTrace();
        }
        // 如果发生异常，返回一个空的列表和400 Bad Request状态码
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            // 使用 questionDAO 的 findAllByCategory 方法来获取指定类别的题目
            return new ResponseEntity<>(questionDAO.findAllByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDAO.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions = questionDAO.findRandomQuestionsByCategory(category, numQ);
        
        return new ResponseEntity<>(questions, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrapper = new ArrayList<>();

        for(Integer id : questionIds){
            Question question = questionDAO.findById(id).get();
            QuestionWrapper qWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            wrapper.add(qWrapper);
        }

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response r: responses) {
            Question question = questionDAO.findById(r.getId()).get();
            if(r.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
