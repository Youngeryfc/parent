package com.yfc.serviceImpl;


import com.alibaba.fastjson.JSONObject;
import com.yfc.baseService.CounterService;
import com.yfc.common.base.BaseController;
import com.yfc.common.base.BasePojo;
import com.yfc.common.base.Counter;
import com.yfc.common.constant.Constants;
import com.yfc.common.utils.MD5Util;
import com.yfc.common.utils.TokenUtil;
import com.yfc.dao.MemberService;
import com.yfc.mapper.UserMapper;
import com.yfc.mq.RegisterMailBoxProducer;
import com.yfc.pojo.UserPo;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yfc
 * @date 2019-06-11
 * @description
 * @parmas
 */
@Slf4j
@RestController
public class MemberServiceImpl extends BaseController implements MemberService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RegisterMailBoxProducer registerMailBoxProducer;

    @Autowired
    private CounterService counterService;
    @Value("${messages.queue}")
    private String ACTIVEMQMSG;

    @Override
    public UserPo getUserById(Integer id) {
        //return null;
        return userMapper.getUserById(id);
    }

    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String,Object> result=new HashMap<>();
        result.put("age",id);
        result.put("name",name);
        return result;

    }

    @Override
    public BasePojo testBase() {
        log.info("============================123");
        log.info("============================12");
        log.info("============================123");
        return setResultSuccess();
    }

    @Override
    public BasePojo setRedis(String key, String value) {
        baseRedis.setString(key,value);
        return setResultSuccess();
    }

    @Override
    public BasePojo getString(String key) {
        return setResultSuccess(baseRedis.getString(key));
    }

    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration 新增用户
     * @Param
     */
    @Transactional
    @Override
    public BasePojo insertUser(UserPo userPo) {
        userPo.setPassword(MD5Util.MD5(userPo.getPassword()));
        int result=userMapper.inserUser(userPo);
        if(result<=0){
            return setResultError("注册失败！");
        }
        //采用异步方式发送消息
        String email=userPo.getEmail();
        String json=emailJson(email);
        log.info("###会员服务推送消息到消息服务平台####json：{}",json);
        //不推送消息
        //sendMsg(json);
        return setResultSuccess();
    }

    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration 拼接json对象--email
     * @Param
     */
    private String emailJson(String email){
        JSONObject root=new JSONObject();

        JSONObject header=new JSONObject();
        header.put("interfaceType", Constants.MSG_EMAIL);
        JSONObject content=new JSONObject();
        content.put("email",email);

        root.put("header",header);
        root.put("content",content);
        return root.toString();
    }


    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration //调用生产者生产消息
     * @Param
     */
    public void sendMsg(String json){
        ActiveMQQueue activeMQQueue=new ActiveMQQueue(ACTIVEMQMSG);
        registerMailBoxProducer.sendMsg(activeMQQueue,json);
    }

    /**
     * @Author yfc
     * @Date 2019-06-19
     * @Despration 使用token令牌登陆
     * @Param
     */
    @Override
    public BasePojo checkLogin(UserPo user){
        //1、验证参数
        String name=user.getUserName();
        String password=user.getPassword();
        if(StringUtils.isEmpty(name)){
            return setResultError("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return setResultError("密码不能为空");
        }
        return setLogin(user);
    }


    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration 根据token查找用户信息
     * @Param
     */
    @Override
    public BasePojo findUserByToken(String token) {
        //1、验证参数
        if(StringUtils.isEmpty(token)){
            return setResultError("token不能为空");
        }
        //2、从redis中使用token查找清楚userUid
        String userUid=baseRedis.getString(token);
        if(StringUtils.isEmpty(userUid)){
            return setResultError("token无效或者已经过期！");
        }
        //3、使用userUid数据库查询用户信息返回给客户端
        UserPo userPo=userMapper.getUserByUserUId(userUid);
        if(userPo==null){
            return setResultError("该用户不存在！");
        }
        //用户密码不返回
        userPo.setPassword(null);
        return setResultSuccess(userPo);
    }

    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration 创建唯一id
     * @Param
     */
    @Override
    public  synchronized String createUniqueId(String table){
        Counter counter=new Counter("TAB_SQL",table);
        counter.setFirstValue(10000);
        int cntValue=counterService.getCntValue(counter);
        return String.valueOf(cntValue);
    }

    /**
     * @Author yfc
     * @Date 2019-06-21
     * @Despration 根据openid查找用户
     * @Param
     */
    @Override
    public BasePojo findUserByOpenId(String openId) {
        //1、验证参数
        if(StringUtils.isEmpty(openId)){
            return setResultError("openId不能为空！");
        }
        //2、使用openId查找数据库对应的数据信息
        UserPo userPo=userMapper.findUserByOpenId(openId);
        if(userPo==null){
            return setResultError("openId没有关联用户");
        }
        //3、自动登陆
        return setLogin(userPo);
    }

    public BasePojo setLogin(UserPo userPo){
        if(userPo==null ){
            return setResultError("账号或密码错误！");
        }
        //3、密码正确，生成token
        String memberToken=TokenUtil.getMemberToken();

        //获取用户id
        UserPo user=userMapper.checkLogin(userPo.getUserName());

        //4、存放在redis中、key为token  value为useruid
        baseRedis.setString(memberToken,user.getUserUid(),Constants.TOKEN_TIME);
        log.info("value:"+baseRedis.getString(memberToken));

        //5、直接返回token
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("memberToken",memberToken);
        return setResultSuccess(jsonObject);
    }


    @Transactional
    @Override
    public BasePojo qqLogin(UserPo userPo) {
        //1、验证参数
        String openid=userPo.getOpenId();
        if(StringUtils.isEmpty(openid)){
            return setResultError("openId不能为空！");
        }
        //2、先进行账号登陆
        BasePojo setLogin=checkLogin(userPo);
        if(!setLogin.getCode().equals(Constants.HTTP_RES_CODE_200)){
            return setLogin;
        }
        //3、自动登陆
        JSONObject jsonObject=(JSONObject)setLogin.getData();
        //4、获取token信息
        String memberToken=jsonObject.getString("memberToken");
        BasePojo userToken=findUserByToken(memberToken);
        if(!userToken.getCode().equals(Constants.HTTP_RES_CODE_200)){
            return userToken;
        }
        UserPo user=(UserPo) userToken.getData();
        //5、修改用户openid
        String userUid=user.getUserUid();
        Integer result=userMapper.updateUserByOpenId(openid,memberToken,userUid);
        if(result<1){
            return setResultError("QQ账号管理失败！");
        }
        return setLogin;
    }
}