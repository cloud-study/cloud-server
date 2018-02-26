package com.cloud.apigateway.controller;

import com.cloud.apigateway.service.DocumentFetchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/26 15:20
 * @description
 */
@RestController
public class SwaggerResourceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ZuulProperties zuulProperties;


    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private DocumentFetchService documentFetchService;

    @ApiIgnore
    @RequestMapping(value = "/swagger-resources")
    ResponseEntity<List<SwaggerResource>> swaggerResources() {
        List<SwaggerResource> resources = new LinkedList<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : zuulProperties.getRoutes().entrySet()) {
            String service = entry.getValue().getServiceId();
            if (service != null) {
                SwaggerResource resource = new SwaggerResource();
                resource.setName(entry.getKey() + ":" + service);
                resource.setSwaggerVersion("2.0");
                resource.setLocation("/docs/" + entry.getKey());
                resources.add(resource);
            }
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiIgnore
    @RequestMapping(value = "/swagger-resources/configuration/ui")
    ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<>(new UiConfiguration(null), HttpStatus.OK);
    }

    @ApiIgnore
    @RequestMapping(value = "/docs/{name}", method = RequestMethod.GET)
    ResponseEntity<ObjectNode> documents(@PathVariable String name, HttpServletRequest request) throws IOException {
        ZuulProperties.ZuulRoute zuulRoute = zuulProperties.getRoutes().get(name);
        //String basePath = zuulRoute.getPath().replace("/**","");
        String basePath = "/";
        String json = documentFetchService.fetch(zuulRoute.getServiceId());

        ObjectNode root = (ObjectNode) MAPPER.readTree(json);
        root.put("basePath", basePath);
        root.put("host", request.getHeader("Host"));
        logger.debug("put basePath:{}, host:{}", root.get("basePath"), root.get("host"));
        return new ResponseEntity<>(root, HttpStatus.OK);
    }

    @ApiIgnore
    @RequestMapping(value = "/swagger-resources/configuration/security")
    ResponseEntity<SecurityConfiguration> securityConfiguration() {
        SecurityConfiguration securityConfiguration =
                new SecurityConfiguration("client", "unknown",
                        "default", "default", "token",
                        ApiKeyVehicle.HEADER, "token", ",");
        return new ResponseEntity<>(securityConfiguration, HttpStatus.OK);
    }
}
