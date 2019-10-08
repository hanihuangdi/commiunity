package com.example.demo.enmus;

public enum CommentTypeEnum {
    QUSTION(1),COMMENT(2);
    private Integer type;
    CommentTypeEnum(Integer type){
        this.type = type;
    }
/*判断回复的类型是否正确*/
    public static boolean isType(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.getType()==type){
            return true;
            }
        }
            return false;
    }

    public Integer getType() {
        return type;
    }
}
