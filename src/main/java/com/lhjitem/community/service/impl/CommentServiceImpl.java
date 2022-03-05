package com.lhjitem.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhjitem.community.dto.CommentDTO;
import com.lhjitem.community.enums.CommentEnum;
import com.lhjitem.community.enums.NotificationEnum;
import com.lhjitem.community.enums.NotificationStatusEnum;
import com.lhjitem.community.exception.ErrorCodeImpl;
import com.lhjitem.community.exception.MyException;
import com.lhjitem.community.mapper.CommentMapper;
import com.lhjitem.community.mapper.NotificationMapper;
import com.lhjitem.community.mapper.QuestionMapper;
import com.lhjitem.community.mapper.UserMapper;
import com.lhjitem.community.model.Comment;
import com.lhjitem.community.model.Notification;
import com.lhjitem.community.model.Question;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duoduo
 * @since 2022-02-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;


    /**
     *
     * @param comment
     * @param commentator  这个参数就是获取到评论人的各种信息
     */
    @Override
    @Transactional  //这个注解就是将该方法的全部操作加上事务管理
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId() == null || comment.getParentId() == 0)
            throw new MyException(ErrorCodeImpl.TARGET_NOT_FOUND);

        if(comment.getType() == null || !CommentEnum.isExist(comment.getType())){
            throw new MyException(ErrorCodeImpl.TYPE_ERROR);
        }

        if(comment.getType() == CommentEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if(dbComment == null)
                throw new MyException(ErrorCodeImpl.COMMENT_ERROR);

            //回复问题
            Question question = questionMapper.selectById(dbComment.getParentId());
            if(question == null) {
                throw new MyException(ErrorCodeImpl.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //增加评论数
            dbComment.setCommentCount(1L);
            commentMapper.addCommentCount(dbComment);

            //创建显示回复评论的通知
            createNotification(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_COMMENT, question.getId());

        }else{
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if(question == null) {
                throw new MyException(ErrorCodeImpl.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1L);
            questionMapper.addCommentCount(question);

            //创建显示回复问题的通知
            createNotification(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_QUESTION, question.getId());

        }
    }


    private void createNotification(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationEnum notificationType, Integer outerId) {
        //自己给自己评论的时候不用提醒
        if(receiver == comment.getCommentator()){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    @Override
    public List<CommentDTO> listByTargetId(Integer id, Integer type) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        //别放了还要区分一级评论和二级评论哦
        //注意eq的第一个参数代表数据库的字段名字，是要遵守完全一致的要不然匹配不上
        queryWrapper.eq("parent_id", id).eq("type", type).orderByDesc("id");
        List<Comment> comments = commentMapper.selectList(queryWrapper);

        if (comments.size() ==0){
            return new ArrayList<>();
        }

        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment-> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为Map
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", userIds);
        List<User> users = userMapper.selectList(userQueryWrapper);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream( ).map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
            return commentDTOS;
    }
}
