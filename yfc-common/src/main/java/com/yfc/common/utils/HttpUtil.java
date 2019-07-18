package com.yfc.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @创建人 yfc
 * @创建时间 2019-07-17 14:11
 * @描述
 */
public class HttpUtil {
	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String PATH_SIGN          = "/";
	public static final String METHOD_POST        = "POST";
	public static final String METHOD_GET         = "GET";
	public static final String METHOD_DELETE      = "DELETE";
	public static final String METHOD_PUT         = "PUT";
	public static final String CONTENT_TYPE       = "Content-Type";
	static {
		System.setProperty("sun.net.client.defaultConnectTimeout", "50000");
		System.setProperty("sun.net.client.defaultReadTimeout", "50000");
	}

	/**
	 * 获取客户端IP
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取对应ip的物理网卡地址
	 *
	 * @param ip
	 * @return
	 */
	public String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public static String getRootPath(ServletContext sctx) {
		String rootpath = sctx.getRealPath("/");
		if (rootpath != null) {
			rootpath = rootpath.replaceAll("\\", "/");
		} else {
			rootpath = "/";
		}
		if (!rootpath.endsWith("/")) {
			rootpath = rootpath + "/";
		}
		return rootpath;
	}

	@SuppressWarnings("unused")
	private static String inputStreamToString(InputStream is, String encode) throws Exception {
		StringBuffer buffer = new StringBuffer();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, encode));
		int ch;
		for (int length = 0; (ch = rd.read()) > -1; length++) {
			buffer.append((char) ch);
		}
		rd.close();
		return buffer.toString();
	}

	/**
	 * 发送get请求，获取返回html
	 *
	 * @param strUrl
	 *            请求地址
	 * @param encode
	 *            页面编码
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String strUrl, String encode) throws Exception {
		URL newUrl = new URL(strUrl);
		HttpURLConnection hConnect = (HttpURLConnection) newUrl.openConnection();
		InputStream is = hConnect.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		hConnect.disconnect();
		return str;
	}

	/**
	 * 发送delete请求，获取返回html
	 *
	 * @param strUrl
	 *            请求地址
	 * @param encode
	 *            页面编码
	 * @return
	 * @throws Exception
	 */
	public static String sendDeleteRequest(String requestUrl, String encode) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_DELETE);// 提交模式
		conn.setConnectTimeout(5000);// 连接超时 单位毫秒
		conn.setReadTimeout(5000);// 读取超时 单位毫秒
		conn.setDoInput(true);
		conn.setUseCaches(false);

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * 发送post请求
	 *
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            请求参数 text1=aaa&text2=bbb
	 * @param encode
	 *            请求参数及页面的编码
	 * @return 返回页面返回的html
	 * @throws Exception
	 */
	public static String sendPostRequest(String requestUrl, String params, String encode) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_POST);// 提交模式
		conn.setConnectTimeout(50000);// 连接超时 单位毫秒
		conn.setReadTimeout(50000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.setUseCaches(false);

		byte[] b = params.toString().getBytes(encode);
		OutputStream os = conn.getOutputStream();
		os.write(b);// 输入参数
		os.flush();
		os.close();

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * 发送post请求
	 *
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            Map类型的参数
	 * @param encode
	 *            请求参数及页面的编码
	 * @return String
	 * @throws Exception
	 */
	public static String sendPostRequest(String requestUrl, Map<String, String> params, String encode) throws Exception {
		StringBuffer paramStr = new StringBuffer("");
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				paramStr.append(key);
				paramStr.append("=");
				paramStr.append(encode(params.get(key)));
				paramStr.append("&");
			}
		}
		return sendPostRequest(requestUrl, paramStr.toString(), encode);
	}

	public static String sendPostRequest(String requestUrl, JSONObject params, String encode) throws Exception {

		StringBuffer paramStr = new StringBuffer("");
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				paramStr.append(key);
				paramStr.append("=");
				paramStr.append(params.get(key));
				paramStr.append("&");
			}
		}
		return sendPost(requestUrl, paramStr.toString());
	}



	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送put请求
	 *
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            请求参数 text1=aaa&text2=bbb
	 * @param encode
	 *            请求参数及页面的编码
	 * @return 返回页面返回的html
	 * @throws Exception
	 */
	public static String sendPutRequest(String requestUrl, String params, String encode) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_PUT);// 提交模式
		conn.setConnectTimeout(5000);// 连接超时 单位毫秒
		conn.setReadTimeout(5000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.setUseCaches(false);

		byte[] b = params.toString().getBytes(encode);
		OutputStream os = conn.getOutputStream();
		os.write(b);// 输入参数
		os.flush();
		os.close();

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}



	/**
	 * url解码
	 *
	 * @param str
	 * @return 解码后的字符串,当异常时返回原始字符串。
	 */
	public static String decode(String url) {
		if (url == null) {
			return null;
		}
		try {
			return URLDecoder.decode(url, CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			return url;
		}
	}

	/**
	 * url编码
	 *
	 * @param str
	 * @return 编码后的字符串,当异常时返回原始字符串。
	 */
	public static String encode(String url) {
		if (url == null) {
			return null;
		}
		try {
			return URLEncoder.encode(url, CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			return url;
		}
	}

	public static void main(String[] args) {
		String str = "";
		try {
			//str = HttpUtil.sendPostRequest("http://192.168.18.216:4002/app/manager", "target=refresh_cs", CHARACTER_ENCODING);

			str = HttpUtil.sendGetRequest("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx4a068c3781d03658&corpsecret=eDYUvBdLtiXcwALIGi6y5-nYs-2ZKFwp8kaf5kv_8YyZKvaOjKQCUQiEm89t0e5q", CHARACTER_ENCODING);
		} catch (Exception e) {
			System.out.println("aaa" + e);
		}
		System.out.println(str);
	}
}
