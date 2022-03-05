package com.lhjitem.community.controller;

import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.service.QuestionService;
import com.lhjitem.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class IndexController {

    @Autowired
//    private QuestionMapper questionMapper;
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search){


        /*注意此时我们用到了QuestionDTO，但是显然我们的Mapper是针对question表的，无法满足功能，这时service层应运而生*/
        PageDTO pageDTO = questionService.showQuestion(search, page, size);
        model.addAttribute("pageDTO", pageDTO);
        //由于初始分页不会携带search信息进行页面跳转，导致每次的分页跳转搜索信息及结果都会被覆盖
        model.addAttribute("search", search);
        return "index";
    }
}
