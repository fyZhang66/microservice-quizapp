package com.kayce.quiz_service.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kayce.quiz_service.model.Quiz;

public interface QuizDAO extends JpaRepository<Quiz, Integer> {} 