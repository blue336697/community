package com.lhjitem.community.exception;

/**
 * @author lhj
 * @create 2022/2/21 14:19
 * 在抽取出一层接口，来避免过多的类对应不同的异常，将最基础最通用的抽取出来，然后使用不同的异常枚举再去加工
 */
public interface ErrorCode {
    String getMessage();
    Integer getCode();
}
