package com.lhjitem.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhjitem.community.dto.NotificationDTO;
import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.enums.NotificationEnum;
import com.lhjitem.community.enums.NotificationStatusEnum;
import com.lhjitem.community.exception.ErrorCodeImpl;
import com.lhjitem.community.exception.MyException;
import com.lhjitem.community.mapper.NotificationMapper;
import com.lhjitem.community.mapper.UserMapper;
import com.lhjitem.community.model.Notification;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duoduo
 * @since 2022-03-01
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public PageDTO showNotification(Integer id, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();
        Integer totalCount = notificationMapper.selectCount(new QueryWrapper<Notification>());

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
        Integer offset = size * (page - 1);

        QueryWrapper<Notification> notificationQueryWrapper = new QueryWrapper<>();
        notificationQueryWrapper.eq("receiver", id).last("limit "+offset+","+size).orderByDesc("id");
        List<Notification> notifications = notificationMapper.selectList(notificationQueryWrapper);

        if(notifications.size() == 0){
            return pageDTO;
        }

        //去重的userid
        /*Set<Integer> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
        ArrayList<Integer> userIds = new ArrayList<>(disUserIds);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id",userIds);
        List<User> users = userMapper.selectList(userQueryWrapper);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));*/

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }




        pageDTO.setData(notificationDTOS);
        return pageDTO;
    }

    @Override
    public Integer getUnreadCount(Integer id) {
        QueryWrapper<Notification> notificationQueryWrapper = new QueryWrapper<>();
        notificationQueryWrapper.eq("receiver",id).eq("status", NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.selectCount(notificationQueryWrapper);
    }

    @Override
    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectById(id);

        if(notification == null)
            throw new MyException(ErrorCodeImpl.NOTIFICATION_NOT_FOUND);

        if(!notification.getReceiver().equals(user.getId()))
            throw new MyException(ErrorCodeImpl.READ_NOTIFICATION_FAIL);

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        //更新通知信息为已读
        notificationMapper.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));



        return notificationDTO;

    }
}
