package com.lhjitem.community.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @auther lhj
 * @date 2022/1/23 19:10
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    //用户头像直接从github的头像获取
    private String avatarUrl;
}
