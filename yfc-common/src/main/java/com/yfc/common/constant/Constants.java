package com.yfc.common.constant;

/**
 * @author yfc
 * @date 2019-06-11
 * @description
 * @parmas
 */
public interface Constants {
    // 响应code
    String HTTP_RES_CODE_NAME = "code";
    // 响应msg
    String HTTP_RES_CODE_MSG = "msg";
    // 响应data
    String HTTP_RES_CODE_DATA = "data";
    // 响应请求成功
    String HTTP_RES_CODE_200_VALUE = "success";
    // 系统错误
    String HTTP_RES_CODE_500_VALUE = "fail";
    // 响应请求成功code
    Integer HTTP_RES_CODE_200 = 200;
    // 系统错误
    Integer HTTP_RES_CODE_500 = 500;

    //发送邮件
    String MSG_EMAIL="email";

    //会员token
    String TOKEN_MEMBER="TOKEN_MEMBER";
    //用户token有效期
    Long TOKEN_TIME=(long) 60*60*24*90;
    Integer COOKIE_TOKEN_TIME= 60*60*24*90;
}
