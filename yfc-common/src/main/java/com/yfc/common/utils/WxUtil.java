package com.yfc.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @创建人 yfc
 * @创建时间 2019-07-17 13:59
 * @描述
 */
@Slf4j
public class WxUtil {
	private static String accessToken = null;
	private static Date accessTokenGetTime = null;
	private static String tokenUrl = null;
	private static String ticket = null;
	private static Date ticketGetTime = null;
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	//签名生成，拼接成string1，对string1作sha1加密
	public static String create_signature(String url,String nonce_str,String timestamp)throws Exception{
		String signature = "";
		String jsapi_ticket =getJSApiTicket();
		String signStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr="+ nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			//对string1进行sha1签名
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(signStr.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}

	//获取签名凭证 jsapi_ticket是公众号用于调用微信JS接口的临时票据
	public static String getJSApiTicket() throws Exception{
		Date now = new Date();
		if (ticket != null && ticketGetTime != null
				&& now.getTime() - ticketGetTime.getTime() <= 1000 * 60 * 30) {
			return ticket;
		} else {
			String acess_token = getAccessToken();
			String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + acess_token + "&type=jsapi";
			String webStr = HttpUtil.sendGetRequest(urlStr,HttpUtil.CHARACTER_ENCODING);
			Map map = JSON.parseObject(webStr);
			String ticketStr = (String) map.get("ticket");
			ticket = ticketStr;
			ticketGetTime = now;
			return ticketStr;
		}
	}

	//获取企业号调用的临时token
	public static String getAccessToken()throws Exception{
		Date now = new Date();
		if(tokenUrl != null){
			return tokenUrl;
		}
		tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx3d7c7ee3cdba0d82&secret=de41d135f5982d45e0fdf11c93e12bb0";
		if (accessToken != null
				&& accessTokenGetTime != null
				&& now.getTime() - accessTokenGetTime.getTime() <= 1000 * 60 * 30) {
			log.debug("accessToken,webStr=" + accessToken);
			return accessToken;
		} else {
			String webStr = HttpUtil.sendGetRequest(tokenUrl, HttpUtil.CHARACTER_ENCODING);
			log.debug("getAccessToken,webStr=" + webStr);
			Map map = JSON.parseObject(webStr);
			String token = (String) map.get("access_token");
			accessToken = token;
			accessTokenGetTime = now;
			log.debug("重新获取的accessToken,webStr=" + accessToken);
			return token;
		}
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
