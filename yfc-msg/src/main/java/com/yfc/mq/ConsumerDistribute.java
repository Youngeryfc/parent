package com.yfc.mq;

import com.alibaba.fastjson.JSONObject;
import com.yfc.common.constant.Constants;
import com.yfc.msg.MessageAdapter;
import com.yfc.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author yfc
 * @date 2019-06-17
 * @description 监听接口(消费者消费信息)
 * @parmas
 */
@Component
@Slf4j
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;
    private MessageAdapter messsageAdpter;
    /**
     * @Author yfc
     * @Date 2019-06-17
     * @Despration 监听消息队列中是否有消息
     * @Param
     */
    @JmsListener(destination = "messages_queue")
    public void distribute(String json){
        log.info("####消息服务平台接收消息：#####"+json);
        if(StringUtils.isEmpty(json)){
            return;
        }
        JSONObject rootJson=new JSONObject().parseObject(json);
        JSONObject header=rootJson.getJSONObject("header");
        String interfaceType= header.getString("interfaceType");

        if(StringUtils.isEmpty(interfaceType)){
            return;
        }
        //判断是否是邮件接口
        if(interfaceType.equals(Constants.MSG_EMAIL)){
            messsageAdpter=emailService;
        }
        if(messsageAdpter==null){
            return;
        }
        JSONObject contentJson=rootJson.getJSONObject("content");
        messsageAdpter.sendMessage(contentJson);
    }
}
