package com.lhjitem.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther lhj
 * @date 2022/1/23 18:22
 * 我们要调用github的登录信息，就要有令牌机制来验证，来传输令牌的信息
 * dto：网络中数据传输对象
 */

@Getter
@Setter

public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
