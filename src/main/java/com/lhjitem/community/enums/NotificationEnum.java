package com.lhjitem.community.enums;

/**
 * @author lhj
 * @create 2022/3/1 20:27
 */
public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for (NotificationEnum value : NotificationEnum.values()) {
            if(value.getType() == type)
                return value.getName();
        }
        return "";
    }
}
