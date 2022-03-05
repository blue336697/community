package com.lhjitem.community.enums;

/**
 * @author lhj
 * @create 2022/2/21 18:24
 */
public enum CommentEnum {
    QUESTION(1),
    COMMENT(2);

    public static boolean isExist(Integer type) {
        for (CommentEnum value : CommentEnum.values()) {
            if(value.getType() == type)
                return true;
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    private Integer type;

    CommentEnum(Integer type) {
        this.type = type;
    }
}
