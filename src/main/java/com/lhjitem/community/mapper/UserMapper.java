package com.lhjitem.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhjitem.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @auther lhj
 * @date 2022/1/23 22:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /*@Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);*//*不是类的时候需要加上param注解*//*

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);*/
}
