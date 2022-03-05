package com.lhjitem.community.dto;

import lombok.Data;

/**
 * @author lhj
 * @create 2022/3/4 23:39
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
