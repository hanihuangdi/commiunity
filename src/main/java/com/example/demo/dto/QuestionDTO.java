package com.example.demo.dto;

import com.example.demo.model.Question;
import com.example.demo.model.User;
import lombok.Data;

import java.util.List;
@Data
public class QuestionDTO {
    Question question;
    User user;
}
