package com.lhjitem.community.dto;

import com.lhjitem.community.model.User;
import lombok.Data;

/**
 * @auther lhj
 * @date 2022/1/26 12:58
 * 用于连接Question和User对象之间字段，使之能够相互使用
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Long viewCount;
    private Long commentCount;
    private Long likeCount;
    private User user;
}
