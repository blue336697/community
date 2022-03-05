package com.lhjitem.community.enums;

/**
 * @author lhj
 * @create 2022/3/1 20:41
 * 这是表示通知的状态，即未读或已读
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
