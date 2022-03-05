package com.lhjitem.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.dto.QuestionDTO;
import com.lhjitem.community.dto.QuestionQueryDTO;
import com.lhjitem.community.exception.ErrorCodeImpl;
import com.lhjitem.community.exception.MyException;
import com.lhjitem.community.mapper.QuestionMapper;
import com.lhjitem.community.mapper.UserMapper;
import com.lhjitem.community.model.Question;
import com.lhjitem.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther lhj
 * @date 2022/1/26 13:01
 * 在service层，就可以将不同的mapper连接在一起完成更加复杂的请求
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements com.lhjitem.community.service.QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageDTO showQuestion(String search, Integer page, Integer size) {

        if(StringUtils.isNotBlank(search)){
            String[] s = StringUtils.split(search, " ");
            search = Arrays.stream(s).collect(Collectors.joining("|"));
        }

        PageDTO<QuestionDTO> pageDTO = new PageDTO<>();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionMapper.countBySearch(questionQueryDTO);
        //全部页数
        Integer totalPage;

        //显示的总页数
        if(totalCount % size == 0)
            totalPage = totalCount / size;
        else
            totalPage = totalCount / size + 1;


        if(page < 1) {
            page = 1;
        }


        if(page > totalPage) {
            page = totalPage;
        }

        //在分页类中直接封装处理页面的方法
        pageDTO.setPagination(totalPage, page);

        //数据库查询的偏移量，即（limit offset， size）
        //这里做一下健壮性处理，如果page等于0时不做分类，则会使偏移量为-5 报异常
        Integer offset = 0;
        if(page == 0)
            offset = 0;
        else
            offset = size * (page - 1);

//        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
//        questionQueryWrapper.last("limit "+offset+","+size).orderByDesc("id");
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionMapper.selectBySearch(questionQueryDTO);

        List<QuestionDTO> questionDTOS = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setData(questionDTOS);

        return pageDTO;
    }


    /**
     * 这里就是查询对应作者的问题列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageDTO showQuestion(Integer userId, Integer page, Integer size) {
        PageDTO<QuestionDTO> pageDTO = new PageDTO<>();
        Integer totalCount = questionMapper.selectCount(new QueryWrapper<Question>());

        //全部页数
        Integer totalPage;

        //显示的总页数
        if(totalCount % size == 0)
            totalPage = totalCount / size;
        else
            totalPage = totalCount / size + 1;


        if(page < 1) {
            page = 1;
        }


        if(page > totalPage) {
            page = totalPage;
        }
        //在分页类中直接封装处理页面的方法
        pageDTO.setPagination(totalPage, page);

        //数据库查询的偏移量，即（limit offset， size）
        Integer offset = 0;
        if(page == 0)
            offset = 0;
        else
            offset = size * (page - 1);

        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("creator", userId).last("limit "+offset+","+size).orderByDesc("id");
        List<Question> questions = questionMapper.selectList(questionQueryWrapper);
        List<QuestionDTO> questionDTOS = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setData(questionDTOS);

        return pageDTO;
    }

    @Override
    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.selectById(id);
        if (question == null){
            throw new MyException(ErrorCodeImpl.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        User user = userMapper.selectById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }


    /**
     * 来查验重复问题
     * @param question
     */
    @Override
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            int i = questionMapper.updateById(question);
            //返回的值如果不是1就代表更新失败
            if (i != 1)
                throw new MyException(ErrorCodeImpl.QUESTION_NOT_FOUND);
        }
    }

    @Override
    public void addView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionMapper.addView(question);
    }

    /**
     * 根据tag查询相关内容
     * @param questionDTO
     * @return
     */
    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag()))
            return new ArrayList<>();
        String replaceTag = StringUtils.replace(questionDTO.getTag(), ",", "|");
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(replaceTag);

        List<Question> questions = questionMapper.selectRelated(question);

        return questions.stream().map(q->{
            QuestionDTO result = new QuestionDTO();
            BeanUtils.copyProperties(q, result);
            return result;
        }).collect(Collectors.toList());

    }


}
