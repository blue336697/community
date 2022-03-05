package com.lhjitem.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhjitem.community.dto.CommentDTO;
import com.lhjitem.community.model.Comment;
import com.lhjitem.community.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duoduo
 * @since 2022-02-21
 */
public interface CommentService extends IService<Comment> {

    void insert(Comment comment, User user);

    List<CommentDTO> listByTargetId(Integer id, Integer type);
}
