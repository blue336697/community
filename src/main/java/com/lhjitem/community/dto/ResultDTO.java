package com.lhjitem.community.dto;

import com.lhjitem.community.exception.ErrorCodeImpl;
import com.lhjitem.community.exception.MyException;
import lombok.Data;

import java.util.List;

/**
 * @author lhj
 * @create 2022/2/21 18:01
 * 返回成功与否的对象
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);return resultDTO;
    }

    public static ResultDTO errorOf(ErrorCodeImpl errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(MyException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO successOf() {
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    /**
     * 显示二级评论的回传页面方法
     * 泛型方法要求的参数是Class<T>类型
     * 为什么要使用泛型方法呢？因为泛型类要在实例化的时候就指明类型，如果想换一种类型，不得不重新new一次，可能不够灵活；而泛型方法可以在调用的时候指明类型，更加灵活。
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResultDTO successOf(T t) {
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
