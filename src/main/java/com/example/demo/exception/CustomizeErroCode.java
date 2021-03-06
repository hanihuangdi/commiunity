package com.example.demo.exception;

public enum CustomizeErroCode implements ICustomizeErrorCode {
    SUCCESS(200,"回复成功"),
    QUESTION_NOT_FOUND(201,"问题找不到了，换一个试试吧"),
    TAGET_PRAME_NOT_FOUND(202,"您没有选中任何问题回复"),
    NOT_lOGIN(203,"账户未登录"),
    SERVICE_ERROR(204,"服务器异常，系统冒烟了"),
    TAGET_PRAME_ERROR(205,"评论类型错误"),
    COMMENT_NOT_FIND(206,"您回复的评论不存在，换一个试试"),
    COMMENT_NULL(207,"输入的内容不能为空"),
    COMMENT_FOUND(208,"当前评论为空"),
    NOTIFICATION_NO_FOUND(209,"您当前的通知不存在，换一个试试"),
    NOTIFICATION_FAILD(210,"您这是访问了别人的连接吧")
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
