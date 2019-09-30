package com.example.demo.model;


import lombok.Data;

@Data
public class User {
    int id;
    String accountId;
    String name;
    String token;
    Long gmtCreat;
    Long gmtModify;
    String avatar_url;

}
