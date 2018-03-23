package com.cloud.apigateway;

import com.cloud.apigateway.filter.AccessFilter;
import com.cloud.config.EnableAllCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableAllCommonConfig
// 将网关作为资源服务器实际上是各个微服务，但是我们使用Zuul统一代理了，所以就把Resource Server设置在Zuul上了）
//@EnableResourceServer
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}

//	@Bean
//	UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer(LoadBalancerInterceptor loadBalancerInterceptor) {
//		return template -> {
//			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//			interceptors.add(loadBalancerInterceptor);
//			AccessTokenProviderChain accessTokenProviderChain = Stream
//					.of(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
//							new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())
//					.peek(tp -> tp.setInterceptors(interceptors))
//					.collect(Collectors.collectingAndThen(Collectors.toList(), AccessTokenProviderChain::new));
//			template.setAccessTokenProvider(accessTokenProviderChain);
//		};
//	}

}
