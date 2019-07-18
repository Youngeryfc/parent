package com.yfc.service;

import com.alibaba.fastjson.JSONObject;
import com.yfc.msg.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yfc
 * @date 2019-06-17
 * @description 处理第三方邮件
 * @parmas
 */
@Service
@Slf4j
public class EmailService implements MessageAdapter {
    @Value("${msg.subject}")
    private String subject;
    @Value("${msg.text}")
    private String text;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendMessage(JSONObject object) {
        //处理发送邮件
        String email=object.getString("email");
        if(StringUtils.isEmpty(email)){
            return;
        }
        //
        log.info("消息服务平台发送邮件:{}开始", email);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发送方
        simpleMailMessage.setFrom(email);
        //接收方15392892942@163.com
        simpleMailMessage.setTo(email);
        //邮件名称
        simpleMailMessage.setSubject(subject);
        //邮件内容
        simpleMailMessage.setText(text);

        //开始发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("消息服务平台发送邮件：{}结束",email);
    }
}
