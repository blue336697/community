package com.lhjitem.community.controller;


import com.lhjitem.community.dto.CommentCreateDTO;
import com.lhjitem.community.dto.CommentDTO;
import com.lhjitem.community.dto.ResultDTO;
import com.lhjitem.community.enums.CommentEnum;
import com.lhjitem.community.exception.ErrorCodeImpl;
import com.lhjitem.community.model.Comment;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author duoduo
 * @since 2022-02-21
 */
@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    /**
     * 通过RequestBody接收json格式的数据，而通过ResponseBody可以将我们的对象自动序列化成json发送到前端
     * @param commentCreateDTO
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(ErrorCodeImpl.NO_LOGIN);
        }

        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent()))
            return ResultDTO.errorOf(ErrorCodeImpl.COMMENT_CONTENT_EMPTY);

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment, user);
        return ResultDTO.successOf();
    }


    /**
     * 这个方法就是显示评论的回复即二级评论
     * @param id
     * @return
     */
    @GetMapping("/comment/{id}")
    @ResponseBody
    public ResultDTO<List> showSecondaryComment(@PathVariable("id")Integer id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentEnum.COMMENT.getType());
        return ResultDTO.successOf(commentDTOS);
    }
}
