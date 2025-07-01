package com.kayce.question_service.dao;

import org.springframework.stereotype.Repository;

import com.kayce.question_service.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// @Repository 用来标识这个类是一个数据访问层的组件
// 它会被Spring容器扫描到，并且可以被自动注入到其他组件中
// JapRepository 负责提供基本的CRUD操作, 需要指定实体类和主键类型
@Repository 
public interface QuestionDAO extends JpaRepository<Question, Integer> {
   
    // 手动定义方法用来查询category的题目
    List<Question> findAllByCategory(String category); // 根据类别查询所有题目

    // @Query 用来定义自定义的查询语句
    // := 用来绑定参数
    // q.id 仅获取题目ID
    @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ); // 根据类别随机查询题目
    
}
