package com.example.demo.exception;

public enum CustomizeErroCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("问题找不到了，换一个试试吧");

    @Override
    public String getMessage() {
        return message;
    }
    private  String message;
    CustomizeErroCode(String message){
        this.message = message;
    }
}
