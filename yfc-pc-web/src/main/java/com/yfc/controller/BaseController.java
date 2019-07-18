package com.yfc.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yfc
 * @date 2019-06-20
 * @description
 * @parmas
 */
public class BaseController {
    public HttpServletRequest getRequest(){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    public HttpServletResponse getResponse(){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getResponse();
    }

    public HttpSession getSession(){
        return getRequest().getSession();
    }

    public BaseController(){
        super();
    }
}
