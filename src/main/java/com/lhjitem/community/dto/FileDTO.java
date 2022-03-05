package com.lhjitem.community.dto;

import lombok.Data;

/**
 * @author lhj
 * @create 2022/3/4 14:18
 * 支持文件长传的通用类
 */
@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;
}
