package com.cloud.authserver.security.config;

import com.cloud.authserver.security.service.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/27 14:54
 * @description
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    @Value("${oauth2Config.client.clientId}")
    private String clientId;
    @Value("${oauth2Config.client.scope}")
    private String scope;
    @Value("${oauth2Config.client.clientSecret}")
    private String clientSecret;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置两个客户端,一个用于password认证一个用于client认证
        clients.inMemory().withClient(clientId)
//                .resourceIds(DEMO_RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes(scope)
//                .authorities("USER")
                .secret(clientSecret);
                //token有效期为120秒
//                .accessTokenValiditySeconds(6000)
//                .refreshTokenValiditySeconds(6000);
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new RedisTokenStore(redisConnectionFactory))  // 使用redis存token
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService)  // 使用数据库用户数据
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // jwt token
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        security .checkTokenAccess("isAuthenticated()");
        // 没有这个调用oauth/token时，需要加头信息，base64编码。 用户名：client_2，密码：123456
        security.allowFormAuthenticationForClients();
//  异常：{"error": "server_error","error_description": "Handler dispatch failed; nested exception is java.lang.StackOverflowError"}
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userName = authentication.getUserAuthentication().getName();
                final Map<String, Object> additionalInformation = new HashMap<String, Object>();
                additionalInformation.put("user_name", userName);
                additionalInformation.put("test", "test");
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
        };
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), clientSecret.toCharArray())
                .getKeyPair("jwt");
        converter.setKeyPair(keyPair);
        return converter;
    }


}
