package com.lhjitem.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.dto.QuestionDTO;
import com.lhjitem.community.model.Question;

import java.util.List;

/**
 * @author lhj
 * @create 2022/2/21 15:39
 */
public interface QuestionService extends IService<Question> {

    PageDTO showQuestion(String search, Integer page, Integer size);

    PageDTO showQuestion(Integer userId, Integer page, Integer size);

    QuestionDTO getQuestionById(Integer id);

    void createOrUpdate(Question question);

    void addView(Integer id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
