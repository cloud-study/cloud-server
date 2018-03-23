package com.cloud.zauth.security.service.Impl;

import com.cloud.security.exception.JwtAuthenticationException;
import com.cloud.zauth.security.auth.ajax.LoginRequest;
import com.cloud.zauth.security.model.UserContext;
import com.cloud.zauth.security.model.token.*;
import com.cloud.zauth.security.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/3/2 16:22
 * @description
 */
@Service
public class AuthServiceImpl implements IAuthService{
    @Autowired
    private UserDetailsService userService;
    @Autowired
    private JwtTokenFactory tokenFactory;


    @Value("${demo.security.jwt.tokenSigningKey}")
    private String tokenSigningKey;

    @Override
    public TokenDTO accessToken(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UserDetails user = userService.loadUserByUsername(username);

//        if (!encoder.matches(password, user.getPassword())) {
        if (!Objects.equals(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (CollectionUtils.isEmpty(user.getAuthorities())){ throw new InsufficientAuthenticationException("User has no roles assigned");}

        UserContext userContext = UserContext.create(user.getUsername(), new ArrayList<>(user.getAuthorities()));
        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

        return new TokenDTO().setAccessToken(accessToken.getToken()).setRefreshToken(refreshToken.getToken());
    }

    @Override
    public AccessJwtToken refreshToken(String tokenPayload) {
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenSigningKey).orElseThrow(() -> new JwtAuthenticationException("Invalid JwtToken"));
        String subject = refreshToken.getSubject();
        UserDetails userDetails = userService.loadUserByUsername(subject);
        if(CollectionUtils.isEmpty(userDetails.getAuthorities())){
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }
        UserContext userContext = UserContext.create(userDetails.getUsername(), new ArrayList<>(userDetails.getAuthorities()));
        return tokenFactory.createAccessJwtToken(userContext);
    }
}
