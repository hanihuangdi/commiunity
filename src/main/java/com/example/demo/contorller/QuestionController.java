package com.example.demo.contorller;

import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    PageService service;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")int id, HttpServletRequest req, Model md){
        Question question = service.findById(id);
        if(question!=null){

            md.addAttribute("question",question);
        }
        User user = new User();
        return "question";
    }
}
