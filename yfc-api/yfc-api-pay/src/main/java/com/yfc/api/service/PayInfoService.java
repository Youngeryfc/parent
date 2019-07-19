package com.yfc.api.service;

import com.yfc.common.base.BasePojo;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pay")
public interface PayInfoService {

    //创建token支付令牌
    public BasePojo createToken();
    //使用支付令牌查找支付信息
    public BasePojo findPayToken();
}
