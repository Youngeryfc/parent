package com.yfc.msg;

import com.alibaba.fastjson.JSONObject;

/**
 * @author yfc
 * @date 2019-06-17
 * @description
 * @parmas 创建适配器接口
 */
public interface MessageAdapter {
    /**
     * @Author yfc
     * @Date 2019-06-17
     * @Despration 统一发送消息的接口
     * @Param
     */
    public void sendMessage(JSONObject object);
}
