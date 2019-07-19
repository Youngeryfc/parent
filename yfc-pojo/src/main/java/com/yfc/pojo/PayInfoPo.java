package com.yfc.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yfc
 * @date 2019-07-19
 * @description
 * @parmas
 */
@Setter
@Getter
public class PayInfoPo {
    private Integer id;
    /**
     * 支付类型
     */
    private Long typeId;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 第三方平台支付id
     */
    private String platformorderId;
    /**
     * 价格 以分为单位
     */
    private Long price;
    /**
     * 支付来源
     */
    private String source;
    /**
     * 支付状态 0 待支付、1支付成功 、2支付失败
     */
    private Integer state;
    /**
     * 支付报文
     */
    private String payMessage;

    /**
     * 用户userUid
     */
    private String userUid;
    private Date createTime;
    private Date updateTime;
}
