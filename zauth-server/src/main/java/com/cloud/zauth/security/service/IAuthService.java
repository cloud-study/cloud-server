package com.cloud.zauth.security.service;

import com.cloud.zauth.security.auth.ajax.LoginRequest;
import com.cloud.zauth.security.model.token.AccessJwtToken;
import com.cloud.zauth.security.model.token.TokenDTO;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/3/2 16:16
 * @description
 */
public interface IAuthService {
    TokenDTO accessToken(LoginRequest loginRequest);
    AccessJwtToken refreshToken(String tokenPayload);
}
