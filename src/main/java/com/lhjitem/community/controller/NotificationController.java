package com.lhjitem.community.controller;


import com.lhjitem.community.dto.NotificationDTO;
import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.enums.NotificationEnum;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.NotificationService;
import com.lhjitem.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author duoduo
 * @since 2022-03-01
 */
@Controller
public class NotificationController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String aboutMe(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        if(user == null)
            return "redirect:/";

        //校验是否为管理员
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if(NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType())
            return "redirect:/question/"+notificationDTO.getOuterId();
        else
            return "redirect:/";


    }
}
