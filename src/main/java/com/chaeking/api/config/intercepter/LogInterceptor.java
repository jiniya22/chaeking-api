package com.chaeking.api.config.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final String LOG_FORMAT_1 = "====== log interceptor %s called [%s]";
    private static final String LOG_FORMAT_2 = "======= Request URI: (%s) %s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(String.format(LOG_FORMAT_1, "preHandle", getClientIp(request)));
        log.info(String.format(LOG_FORMAT_2, request.getMethod(), request.getRequestURI()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(String.format(LOG_FORMAT_1, "postHandle", getClientIp(request)));
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = "";

        if (request != null) {
            clientIp = request.getHeader("X-FORWARDED-FOR");
            if (Strings.isBlank(clientIp))  clientIp = request.getRemoteAddr();
            if (Strings.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp))	clientIp = request.getHeader("Proxy-Client-clientIp");
            if (Strings.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp))	clientIp = request.getHeader("WL-Proxy-Client-clientIp");
            if (Strings.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp))	clientIp = request.getHeader("HTTP_CLIENT_clientIp");
            if (Strings.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp))	clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (Strings.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp))	clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

}
