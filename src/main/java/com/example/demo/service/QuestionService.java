package com.example.demo.service;

import com.example.demo.mapper.QuestionCustomMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    PageService service;
    @Autowired
    QuestionCustomMapper questionCustomMapper;
    public void update(Question question) {
       // questionMapper.update(question);

        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(question.getId());
        questionMapper.updateByExample(question, example);
    }


    public void insert(Question question) {
        questionMapper.insertSelective(question);
    }
    public void addView(Long id){
        questionCustomMapper.update(id);
    }
}
