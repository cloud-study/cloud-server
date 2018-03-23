package com.cloud.zauth.security.controller;

import com.cloud.security.exception.JwtAuthenticationException;
import com.cloud.security.extractor.TokenExtractor;
import com.cloud.zauth.security.auth.ajax.LoginRequest;
import com.cloud.zauth.security.model.UserContext;
import com.cloud.zauth.security.model.token.JwtTokenFactory;
import com.cloud.zauth.security.model.token.RawAccessJwtToken;
import com.cloud.zauth.security.model.token.RefreshToken;
import com.cloud.zauth.security.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/3/2 14:55
 * @description
 */
@RestController
@RequestMapping("/api/zauth-server")
@Api(value = "jwt Token", description = "jwt Token")
public class TokenController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private TokenExtractor tokenExtractor;


    @RequestMapping(value="/token/refresh", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String tokenPayload = tokenExtractor.extract(request.getHeader("Authorization"));
        return ResponseEntity.ok(authService.refreshToken(tokenPayload));
    }

    @ApiOperation(value = "获取jwt Token", httpMethod = "POST", response = ResponseEntity.class)
    @RequestMapping(value="/token/access", method= RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity accessToken(@RequestBody LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        return ResponseEntity.ok( authService.accessToken(loginRequest));
    }
    @ApiOperation(value = "安全api, 验证", httpMethod = "GET", response = ResponseEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value="/sec/test", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity secTest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> result = new HashMap<>();
        result.put("test", "Auth Server sec test");
        return ResponseEntity.ok(result);

    }
}
