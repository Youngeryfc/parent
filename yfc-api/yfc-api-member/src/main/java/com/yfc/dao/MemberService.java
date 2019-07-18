package com.yfc.dao;

import com.yfc.common.base.BasePojo;
import com.yfc.pojo.UserPo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/test")
public interface MemberService {
    @RequestMapping("/getUserById")
    public UserPo getUserById(Integer id);

    @RequestMapping("/test")
    public Map<String, Object> test(@RequestParam("id") Integer id, @RequestParam("name") String name);

    @RequestMapping("/testBase")
    public BasePojo testBase();

    @RequestMapping("/testRedis")
    public BasePojo setRedis(@RequestParam("key") String key,@RequestParam("value") String value);

    @RequestMapping("/getString")
    public BasePojo getString(String key);

    @RequestMapping("/insert")
    //@RequestBody表明传递的参数是json格式的
    BasePojo insertUser(UserPo user);

    @RequestMapping("/checkLogin")
    BasePojo checkLogin(@RequestBody UserPo user);

    //使用token进行登陆
    @RequestMapping("/findUserByToken")
    BasePojo findUserByToken(String token);

    //新增表id
    @RequestMapping("/createUniqueId")
    String createUniqueId(@RequestBody String table);

    //通过openid查找用户信息
    @RequestMapping("/findUserByOpenId")//
    BasePojo findUserByOpenId(@RequestParam("openId")String openId);

    @RequestMapping("/qqLogin")
    BasePojo qqLogin(@RequestBody UserPo userPo);
}
