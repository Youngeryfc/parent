package com.wx.controller;

import com.yfc.common.utils.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 yfc
 * @创建时间 2019-07-17 14:55
 * @描述
 */
@RestController
@Slf4j
public class WxController {
	public void getWxSignature(String wxSignatureUrl){
		try {
			log.info("coming----");
			String url="http://vl.alfaromeocollegechina.com/web/"+wxSignatureUrl;
			String nonceStr = WxUtil.create_nonce_str();
			String timestamp = WxUtil.create_timestamp();
			String signature = WxUtil.create_signature(url, nonceStr, timestamp);

			log.info("-----------------nonceStr"+ nonceStr);
			log.info("-----------------timestamp"+timestamp);
			log.info("-----------------signature"+signature);
			log.info("-----------------url"+url);
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
