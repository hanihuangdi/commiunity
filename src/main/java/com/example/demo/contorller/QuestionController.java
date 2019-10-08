package com.example.demo.contorller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.PageService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PageService service;
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")int id, HttpServletRequest req, Model md){
        User user = new User();
        Question question = service.findbyid(id);
        questionService.addView(id);
        QuestionDTO questionDTO = new QuestionDTO();
        if(question!=null){
            user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            md.addAttribute("questionDTO",questionDTO);
        }
        return "question";
    }
}
