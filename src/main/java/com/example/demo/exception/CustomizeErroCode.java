package com.example.demo.exception;

public enum CustomizeErroCode implements ICustomizeErrorCode {
    SUCCESS(200,"回复成功"),
    QUESTION_NOT_FOUND(201,"问题找不到了，换一个试试吧"),
    TAGET_PRAME_NOT_FOUND(202,"您没有选中任何问题回复"),
    NOT_lOGIN(203,"账户未登录"),
    SERVICE_ERROR(204,"服务器异常，系统冒烟了"),
    TAGET_PRAME_ERROR(205,"评论类型错误"),
    COMMENT_NOT_FIND(206,"您回复的评论不存在，换一个试试")
    ;


    @Override
    public String getMessage() {
        return message;
    }
    private  String message;
    private  Integer code;
    CustomizeErroCode(Integer code,String message){
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
