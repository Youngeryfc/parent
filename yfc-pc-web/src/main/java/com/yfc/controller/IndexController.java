package com.yfc.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yfc
 * @date 2019-06-19
 * @description
 * @parmas
 */
public class IndexController extends BaseController{
    private static final String INDEX="index";

    /**
     * @Author yfc
     * @Date 2019-06-19
     * @Despration 返回主页 项目启动、直接访问127.0.0.1,跳转到主页（默认端口80不显示），
     * @Param
     */
    @RequestMapping("/")
    public String index(){
        return INDEX;
    }
}
