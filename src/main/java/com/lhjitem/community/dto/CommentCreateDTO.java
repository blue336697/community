package com.lhjitem.community.dto;

import lombok.Data;

/**
 * @author lhj
 * @create 2022/2/21 17:32
 * 这个用于创建评论的通用类
 */
@Data
public class CommentCreateDTO {
    private Integer parentId;
    private String content;
    private Integer type;
}
