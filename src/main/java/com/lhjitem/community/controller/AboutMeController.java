package com.lhjitem.community.controller;

import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.NotificationService;
import com.lhjitem.community.service.QuestionService;
import com.lhjitem.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther lhj
 * @date 2022/1/26 18:58
 */
@Controller
public class AboutMeController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/aboutMe/{action}")
    public String aboutMe(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){
        User user = (User) request.getSession().getAttribute("user");

        if(user == null)
            return "redirect:/";

        if("questions".equals(action)){
            model.addAttribute( "section", "questions");
            model.addAttribute( "sectionName", "我的提问");
            PageDTO pageDTO = questionService.showQuestion(user.getId(), page, size);
            model.addAttribute("pageDTO", pageDTO);
        }else if("replies".equals(action)){
            PageDTO pageDTO = notificationService.showNotification(user.getId(), page, size);
            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute( "section", "replies");
            model.addAttribute( "sectionName", "最新回复");
        }
        return "aboutMe";
    }


}
