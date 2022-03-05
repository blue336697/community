package com.lhjitem.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lhj
 * @create 2022/3/1 18:30
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
