package com.example.demo.enmus;

public enum NotifiStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotifiStatusEnum(int status) {
        this.status = status;
    }
}
