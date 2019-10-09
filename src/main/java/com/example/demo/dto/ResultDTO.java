package com.example.demo.dto;

import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
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
    public static <T> ResultDTO errOf(T data) {
        ResultDTO resultDTO = new ResultDTO(200, "请求成功");
        resultDTO.setData(data);
        return resultDTO;
    }
}
