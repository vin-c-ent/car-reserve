package com.car.config;

import com.car.constants.LogTagConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component("InboundInterceptor")
public class InboundInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(LogTagConstants.REQUEST_URI, request.getRequestURI());
        request.setAttribute(LogTagConstants.REQUEST_START_TIME, System.currentTimeMillis());
        log.info("PreHandle request");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long) request.getAttribute(LogTagConstants.REQUEST_START_TIME);
        request.setAttribute(LogTagConstants.DURATION, System.currentTimeMillis() - startTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            MDC.put(LogTagConstants.STATUS_CODE, String.valueOf(response.getStatus()));
            MDC.put(LogTagConstants.DURATION, String.valueOf(request.getAttribute(LogTagConstants.DURATION)));
            log.info("AfterCompletion request");
        } finally {
            MDC.clear();
        }
    }


}
