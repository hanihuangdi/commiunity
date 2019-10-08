package com.example.demo.dto;

import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static ResultDTO errOf(Integer code,String message){
        return new ResultDTO(code,message);
    }
    ResultDTO(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
