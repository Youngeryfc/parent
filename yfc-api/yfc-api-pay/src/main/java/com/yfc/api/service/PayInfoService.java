package com.yfc.api.service;

import com.yfc.common.base.BasePojo;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pay")
public interface PayInfoService {
    @RequestMapping("/getPayInfo")
    public BasePojo getPayInfo(Integer id);

    //创建token支付令牌
    @RequestMapping("/createToken")
    public BasePojo createToken();
    //使用支付令牌查找支付信息
    @RequestMapping("/findPayToken")
    public BasePojo findPayToken();
}
