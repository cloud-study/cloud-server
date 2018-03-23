package com.cloud.zauth.security.model.token;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/3/2 16:19
 * @description
 */
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public String getAccessToken() {
        return accessToken;
    }

    public TokenDTO setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public TokenDTO setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public TokenDTO setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    @Override
    public String toString() {
        return "TokenDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
