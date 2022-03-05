package com.lhjitem.community.controller;

import com.lhjitem.community.dto.CommentDTO;
import com.lhjitem.community.dto.QuestionDTO;
import com.lhjitem.community.enums.CommentEnum;
import com.lhjitem.community.service.CommentService;
import com.lhjitem.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @auther lhj
 * @date 2022/1/29 17:23
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);

        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        //获得评论
        List<CommentDTO> commentList = commentService.listByTargetId(id, CommentEnum.QUESTION.getType());

        //累加阅读数
        questionService.addView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentList);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
