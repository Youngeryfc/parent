package com.yfc.serviceImpl;

import com.yfc.api.service.PayInfoService;
import com.yfc.common.base.BaseController;
import com.yfc.common.base.BasePojo;
import com.yfc.mapper.PayMapper;
import com.yfc.pojo.PayInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yfc
 * @date 2019-07-19
 * @description
 * @parmas
 */
@RestController
public class PayServiceImpl extends BaseController implements PayInfoService {
    @Autowired
    private PayMapper payMapper;
    @Override
    public BasePojo getPayInfo(Integer id){
        PayInfoPo pay=payMapper.getPayInfoById(id);
        return setResultSuccess(pay);
    }
    //创建支付令牌
    @Override
    public BasePojo createToken() {
        //1、创建支付请求信息
        //2、生成对应token
        //3、存放在redis中，key为token、value为支付id
        //返回token
        return null;
    }

    //使用支付令牌查找支付信息
    @Override
    public BasePojo findPayToken() {
        //1、参数验证
        //2、判断token的有效期
        //3、使用token查找redis对应的支付id
        //4、使用支付id，进行下单
        //5、使用支付id查询支付信息
        //6、对接支付代码、返回提交支付from表单元素给客户端
        return null;
    }
}
