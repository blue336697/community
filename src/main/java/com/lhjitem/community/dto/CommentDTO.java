package com.lhjitem.community.dto;

import com.lhjitem.community.model.User;
import lombok.Data;

/**
 * @author lhj
 * @create 2022/2/21 17:32
 * 这个就是用于显示并且与user相连接的通用评论类
 */
@Data
public class CommentDTO {
    private Integer id;

    private Integer parentId;

    private Integer type;

    private Integer commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private Long commentCount;

    private String content;

    private User user;
}
