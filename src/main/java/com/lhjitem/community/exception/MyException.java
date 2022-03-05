package com.lhjitem.community.exception;

/**
 * @author lhj
 * @create 2022/2/21 14:11
 * 继承RuntimeException是因为可以统一进行trycatch
 */
public class MyException extends RuntimeException{
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public MyException(ErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }
}
