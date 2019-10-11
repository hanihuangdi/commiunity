package com.example.demo.service;

import com.example.demo.mapper.QuestionCustomMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Question> findRelated(Question question) {
        Question question1 = new Question();
        if(question.getTag()==null||question.getTag()==""){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(question.getTag(), "，");
        String tag = StringUtils.join(tags,'|');
        question1.setTag(tag);
        question1.setId(question.getId());
        List<Question> questions = questionCustomMapper.findRelated(question1);
        return questions;

    }
}
