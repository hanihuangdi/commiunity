package com.example.demo.dto;

import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static ResultDTO errOf(CustomizeErroCode erroCode){
        return new ResultDTO(erroCode.getCode(),erroCode.getMessage());
    }
    ResultDTO(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public static ResultDTO okOf(){
        return new ResultDTO(200,"请求成功");
    }

    public static ResultDTO errOf(CustomizeException e) {
        return new ResultDTO(e.getCode(),e.getMessage());
    }
}
