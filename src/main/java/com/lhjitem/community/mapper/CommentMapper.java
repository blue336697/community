package com.lhjitem.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhjitem.community.model.Comment;
import com.lhjitem.community.model.Question;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author duoduo
 * @since 2022-02-21
 */
public interface CommentMapper extends BaseMapper<Comment> {

    int addCommentCount(Comment record);

}
