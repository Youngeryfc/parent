
package com.yfc.controller;

import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.yfc.common.base.BasePojo;
import com.yfc.common.constant.Constants;
import com.yfc.common.utils.CookieUtil;
import com.yfc.fegin.MemberServiceFegin;
import com.yfc.pojo.UserPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import java.util.LinkedHashMap;



/**
 * @author yfc
 * @date 2019-06-20
 * @description
 * @parmas
 */

@Controller
public class LoginController extends BaseController{
    @Autowired
    private MemberServiceFegin memberServiceFegin;
    private static final String INDEX="index";
    private static final String LOGIN="login";
    private static final String QQRELATION="qqrelation";
    //跳转登陆页面
    @RequestMapping(value="/login" ,method = RequestMethod.GET)
    public String loginGet(){
        return LOGIN;
    }

    //登陆请求具体提交
    @RequestMapping(value="/login" ,method = RequestMethod.POST)
    public String loginPost(UserPo user){
        //1、调用登陆接口(验证参数，在接口的实现类中已经验证)
        BasePojo basePojo=memberServiceFegin.checkLogin(user);
        //2、将token信息放在cookie里面
        if(!basePojo.getCode().equals(Constants.HTTP_RES_CODE_200)){
            getRequest().setAttribute("error","账号或密码错误！");
            return LOGIN;
        }
        LinkedHashMap loginData=(LinkedHashMap) basePojo.getData();
        //获取memberToken
        String memberToken=(String )loginData.get("memberToken");
        if(StringUtils.isEmpty(memberToken)){
            getRequest().setAttribute("error","会话失效");
            return LOGIN;
        }
        //3、将token存放在cookies里面
        setCookies(memberToken);
        return INDEX;
    }

    public void  setCookies(String memberToken){
        CookieUtil.addCookie(getResponse(),Constants.TOKEN_MEMBER,memberToken,Constants.COOKIE_TOKEN_TIME);
    }

    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration 生成qq授权登陆连接
     * @Param
     */
    @RequestMapping("/localQQLogin")
    public String qqLogin() throws Exception{
        String authorizeURL=new Oauth().getAuthorizeURL(getRequest());
        return "redirect:"+authorizeURL;
    }

    //回调函数
    @RequestMapping("/qq")
    public String qqLoginCallback() throws Exception{
        //1、获取授权码
        System.out.println("获取授权码："+new Oauth().getAuthorizeURL(getRequest()));
        System.out.println("-------->request:"+getRequest());
        //2、使用授权码code获取accessToken
        AccessToken access=new Oauth().getAccessTokenByRequest(getRequest());
        if(access==null){
            getRequest().setAttribute("error","qq授权失败");
            return "error";
        }
        String accessToken=access.getAccessToken();
        if(accessToken==null){
            getRequest().setAttribute("error","accessToken为null");
            return "error";
        }
        //3、使用accessToken获取openId
        OpenID openIDObj=new OpenID(accessToken);
        String openId=openIDObj.getUserOpenID();
        //4、调用会员服务接口使用userOpenId查找是否已经关联过账号
        BasePojo basePojo=memberServiceFegin.findUserByOpenId(openId);
        if(!basePojo.getCode().equals(Constants.HTTP_RES_CODE_200)){
            //5、如果没有关联账号、跳转到关联账号页面
            getSession().setAttribute("qqOpenId",openId);
            return QQRELATION;
        }
        //6、已经绑定账号、自动登录将用户token信息存在cookie中
        LinkedHashMap data =(LinkedHashMap) basePojo.getData();
        String memberToken=(String)data.get("memberToken");
        if(StringUtils.isEmpty(memberToken)){
            getRequest().setAttribute("error","会话失效！");
            return LOGIN;
        }
        //将token存放在cookie里面
        setCookies(memberToken);
        return INDEX;
    }

    /**
     * @Author yfc
     * @Date 2019-07-11
     * @Despration 登陆请求具体提交实现
     * @Param
     */
    @RequestMapping("/qqRelation")
    public String qqRelation(UserPo userPo){
        //1、获取openid
        String openId=(String) getSession().getAttribute("qqOpenId");
        if(StringUtils.isEmpty(openId)){
            getRequest().setAttribute("error","没有获取到openid");
            return "error";
        }

        //2、调用登陆接口、获取token 信息
        userPo.setOpenId(openId);
        BasePojo basePojo=memberServiceFegin.qqLogin(userPo);
        if(!basePojo.getCode().equals(Constants.HTTP_RES_CODE_200)){
            getRequest().setAttribute("error","账号或者，密码错误！");
            return LOGIN;
        }
        LinkedHashMap logindata=(LinkedHashMap)basePojo.getData();
        String memberToken=(String)logindata.get("memberToken");
        if(StringUtils.isEmpty(memberToken)){
            getRequest().setAttribute("error","会话已经失效！");
            return LOGIN;
        }

        //3、将token信息存放在cookie里面
        setCookies(memberToken);
        return INDEX;
    }

}

