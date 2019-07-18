package com.yfc.controller;

import com.yfc.common.base.BasePojo;
import com.yfc.common.constant.Constants;
import com.yfc.fegin.MemberServiceFegin;
import com.yfc.pojo.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yfc
 * @date 2019-06-19
 * @description
 * @parmas
 */
@Controller
public class RegisterController extends BaseController{
    private static final String REGISTER="register";
    private static final String LOGIN="login";
    @Autowired
    private MemberServiceFegin memberServiceFegin;

    @RequestMapping(value="/register" ,method = RequestMethod.GET)
    public String register(){
        return REGISTER;
    }

    @RequestMapping(value="/register" ,method = RequestMethod.POST)
    public String register(UserPo user){
        System.out.println("---------");
        //生成用户唯一有序id
        String userUid=memberServiceFegin.createUniqueId("User");
        user.setUserUid(userUid);
        //调用会员注册接口
        BasePojo basePojo=memberServiceFegin.insertUser(user);
        //如果失败，返回失败页面
        if(!basePojo.getCode().equals(Constants.HTTP_RES_CODE_200)){
            return REGISTER;
        }
        return LOGIN;
    }
}
