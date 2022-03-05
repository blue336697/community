package com.lhjitem.community.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lhjitem.community.model.User;
import lombok.Data;

/**
 * @author lhj
 * @create 2022/3/1 21:04
 */
@Data
public class NotificationDTO {
    private Integer id;

    private Long gmtCreate;

    private Integer status;

    private Integer notifier;

    private String notifierName;

    private String outerTitle;

    //回复了问题  或  回复了评论
    private String typeName;

    private Integer type;

    private Integer outerId;
}
