package com.lhjitem.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author duoduo
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer notifier;

    private Integer receiver;

    /**
     * 用来区分此通知是点赞通知还是回复评论通知
     */
    private Integer type;

    /**
     * 区分是问题的id还是回复的id等等
     */

    private Integer outerId;

    private Long gmtCreate;

    /**
     * 0是未读，1是已读
     */
    private Integer status;

    private String notifierName;

    private String outerTitle;


}
