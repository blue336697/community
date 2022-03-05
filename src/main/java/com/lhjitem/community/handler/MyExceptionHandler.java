package com.lhjitem.community.handler;

import com.alibaba.fastjson.JSON;
import com.lhjitem.community.dto.ResultDTO;
import com.lhjitem.community.exception.ErrorCodeImpl;
import com.lhjitem.community.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @author lhj
 * @create 2022/2/21 13:41
 * 全局的异常捕获处理类
 */

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Exception e,
                                           Model model,
                                           HttpServletResponse response) {

        //使用json和ajax实时显示错误的原因
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回json
            ResultDTO resultDTO;
            if (e instanceof MyException){
                resultDTO = ResultDTO.errorOf((MyException)e);
            }else {
                resultDTO = ResultDTO.errorOf(ErrorCodeImpl.SYS_ERROR);
            }
            try {
                response.setContentType( "application/json" );
                response.setStatus (200);
                response.setCharacterEncoding( "utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));writer.close();
            }catch ( IOException ioe) {
            }
            return null;


        }else{
            //不是则进行错误页面跳转
            //如果Throwable是自己定义的exception就用自己的错误信息
            if (e instanceof MyException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", ErrorCodeImpl.SYS_ERROR.getMessage());
            }
            model.addAttribute("exception",e);
            return new ModelAndView("error");
        }


    }

}
