package com.example.demo.model;

import lombok.Data;

@Data
public class Question {
    int id;
    String title;
    String description;
    Long gmt_create;
    Long gmt_modify;
    int creator;
    int comment_count;
    int view_count;
    int like_count;
    String tag;
    User user;

}
