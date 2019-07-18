package com.yfc.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @author yfc
 * @date 2019-06-17
 * @description 创建生产者
 * @parmas
 */
@Component
public class RegisterMailBoxProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(Destination destination,String json){
        //生产并发送消息，参数是消息队列和要发送的消息
        jmsMessagingTemplate.convertAndSend(destination,json);
    }
}
