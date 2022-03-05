package com.lhjitem.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhjitem.community.dto.NotificationDTO;
import com.lhjitem.community.dto.PageDTO;
import com.lhjitem.community.model.Notification;
import com.lhjitem.community.model.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duoduo
 * @since 2022-03-01
 */
public interface NotificationService extends IService<Notification> {

    PageDTO showNotification(Integer id, Integer page, Integer size);

    Integer getUnreadCount(Integer id);

    NotificationDTO read(Integer id, User user);
}
