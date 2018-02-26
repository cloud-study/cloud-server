package com.cloud.apigateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/26 15:24
 * @description
 */
@Service
public class DocumentFetchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentFetchService.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 拉取 Service 的 API 信息，支持Spring Retry
     * @param serviceId Service ID
     * @return API Json
     */
    public String fetch(String serviceId) {
        LOGGER.info("fetch: {}", serviceId);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if(instances.isEmpty()){
            throw new RemoteAccessException("service not found " + serviceId);
        }
        ResponseEntity<String> response = restTemplate.getForEntity(
                instances.get(0).getUri() + "/v2/api-docs",
                String.class);
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RemoteAccessException("fetch failed : " + response);
        }
        return response.getBody();
    }

    public String recover(RemoteAccessException exception){
        LOGGER.warn("remote access failed : {}", exception);
        return null;
    }
}
