package com.example.demo.enmus;

public enum NotifiTypeEnum {
    QUESTIONTYPE(1,"回答了问题"),COMMENTTYPE(2,"回答了评论");
    private  Integer typeNum;
   private String typeName;
    NotifiTypeEnum(Integer num,String name){
        typeName = name;
        typeNum = num;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String nameOfType(int type) {
        for (NotifiTypeEnum notificationTypeEnum : NotifiTypeEnum.values()) {
            if (notificationTypeEnum.getTypeNum() == type) {
                return notificationTypeEnum.getTypeName();
            }
        }
        return "";
    }
}
