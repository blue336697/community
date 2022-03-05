package com.lhjitem.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhjitem.community.dto.QuestionDTO;
import com.lhjitem.community.dto.QuestionQueryDTO;
import com.lhjitem.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther lhj
 * @date 2022/1/25 17:34
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    int addView(Question record);

    int addCommentCount(Question record);

    List<Question> selectRelated(Question question);


    @Select("select * from question limit #{offset}, #{size}")
    List<Question> showQuestion(@Param("offset") Integer offset, @Param("size") Integer size);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

    /*@Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);




    /*这里去查了一下count(1)与count(*)比较：

        如果你的数据表没有主键，那么count(1)比count(*)快
        如果有主键的话，那主键（联合主键）作为count的条件也比count(*)要快
        如果你的表只有一个字段的话那count(*)就是最快的啦
        count(*) count(1) 两者比较。主要还是要count(1)所相对应的数据字段。
        如果count(1)是聚索引,id,那肯定是count(1)快。但是差的很小的。
        因为count(*),自动会优化指定到那一个字段。所以没必要去count(?)，用count(*),sql会帮你完成优化的
     */
    /*@Select("select count(1) from question")
    Integer showTotalPage();

    */
    /*这里就是查询对应作者的问题列表*//*
    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> showQuestionById(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);


    @Select("select count(1) from question where creator = #{userId}")
    Integer showTotalPageById(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);*/
}
    