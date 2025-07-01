package com.kayce.quiz_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
// @RequiredArgsConstructor 作用是生成一个包含所有final字段的构造函数
@RequiredArgsConstructor
public class Response {
    private Integer id;
    private String response;
}
