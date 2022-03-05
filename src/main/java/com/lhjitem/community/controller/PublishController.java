package com.lhjitem.community.controller;

import com.lhjitem.community.cache.TagCache;
import com.lhjitem.community.dto.QuestionDTO;
import com.lhjitem.community.model.Question;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.impl.QuestionServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther lhj
 * @date 2022/1/25 14:13
 * 发布页的controller
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    //get请求显示页面
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    //post就是发送表单数据
    @PostMapping("/publish")
    public String publishText(@RequestParam(value = "title", required = false)String title,
                              @RequestParam(value = "description", required = false)String description,
                              @RequestParam(value = "tag", required = false)String tag,
                              /*这里获取id踩个坑，非必须的传入 要把required设为false*/
                              @RequestParam(value = "id", required = false)Integer id,
                              HttpServletRequest request,
                              Model model){

        model.addAttribute("title" ,title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",TagCache.get());

        if(title == null || title ==""){
            model.addAttribute("error", "标题为空");
            return "publish";
        }

        if(description == null || description ==""){
            model.addAttribute("error", "内容为空");
            return "publish";
        }

        if(tag == null || tag ==""){
            model.addAttribute("error", "标签为空");
            return "publish";
        }

        String invalid = TagCache.filterValid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法字符："+invalid);
            return "publish";
        }

        /*把非法字符全部过滤*/
//        StringUtils.replaceAll("tag","\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b",",");



        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionServiceImpl.createOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String editText(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO question = questionServiceImpl.getQuestionById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag",question.getTag());
        //由于question表中没有唯一标识，所以我们手动加一个
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
