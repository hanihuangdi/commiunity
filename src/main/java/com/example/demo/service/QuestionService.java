package com.example.demo.service;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    public void update(Question question) {
        questionMapper.update(question);
    }

    public void insert(Question question) {
        questionMapper.insert(question);
    }
}
