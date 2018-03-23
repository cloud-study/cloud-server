package com.cloud.apigateway.filter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/25 16:01
 * @description 服务过滤
 */
public class AccessFilter extends ZuulFilter{

    private static final Logger log = LoggerFactory.getLogger(AccessFilter.class);


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0; //数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        boolean shouldFilter = false;

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getRequestURI().contains("/sec/")) {
            shouldFilter = true;
        }
        return shouldFilter;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        log.debug(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        String authBody = request.getHeader("Authorization");
        if(authBody == null ||
                !request.getHeader("Authorization").startsWith("Bearer ") ){
            this.unauthorized();
        }else{
            context.addZuulRequestHeader("Authorization", authBody);
        }
        return null;
    }

    private void unauthorized(){
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JSONObject  body = new JSONObject();

        try {
            body.put("code", "UNAUTHORIZED");
            body.put("message", "Request without authorization header.");
            body.put("success", false);
            body.put("total", 0);
            response.getWriter().write(body.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        context.setSendZuulResponse(false);
//        throw new RuntimeException("filter error");
    }
}
