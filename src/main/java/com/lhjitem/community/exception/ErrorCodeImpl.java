package com.lhjitem.community.exception;

/**
 * @author lhj
 * @create 2022/2/21 14:21
 * 以枚举类来存储常用错误信息
 */
public enum ErrorCodeImpl implements ErrorCode{
    QUESTION_NOT_FOUND(2001, "问题不存在，搜索别的关键字试试~"),
    TARGET_NOT_FOUND(2002, "未选择任何问题或评论进行回复！"),
    NO_LOGIN(2003, "请登录后重试此操作！"),
    SYS_ERROR(2004, "服务器冒烟了，请稍后试试！"),
    TYPE_ERROR(2005, "评论类型错误或不存在！"),
    COMMENT_ERROR(2006, "回复的评论不存在！"),
    COMMENT_CONTENT_EMPTY(2007, "评论内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息还就那个不翼而飞！")
    ;

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    ErrorCodeImpl(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
