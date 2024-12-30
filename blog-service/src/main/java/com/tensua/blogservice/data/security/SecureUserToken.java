package com.tensua.blogservice.data.security;

import lombok.Data;

/**
 * 用户 Token 封装实体
 */
@Data
public class SecureUserToken {

    /**
     * Token
     */
    private String token;

    /**
     * 基本信息
     */
    private OauthUserInfo secureUser;

    /**
     * 创建时间
     */
    private long createTimestamp = System.currentTimeMillis();

}
