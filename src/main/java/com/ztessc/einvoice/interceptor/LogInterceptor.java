package com.ztessc.einvoice.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ztessc.einvoice.service.LogService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.JwtUtil;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.UuidUtil;

public class LogInterceptor extends HandlerInterceptorAdapter {
	
	Logger log = Log.get();
	
	@Autowired
	private LogService logService;
	
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	private static Map<String, String> URL_MAP = new HashMap<String, String>();
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.debug("===================开始请求 " + request.getRequestURL());
		
		try {
			//请求之前记录开始时间，用于记录请求总共耗时
			long beginTime = System.currentTimeMillis();
			//线程绑定变量（该数据只有当前请求的线程可见）
	        startTimeThreadLocal.set(beginTime);
			
		} catch (Exception e) {
			log.error("",e);
		}
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
			//请求url地址
			String urlId = this.getUrlId(request);
			
			//记录已埋点点操作日志
			if(StringUtils.isNotBlank(urlId)) {
				//请求开始时间
				long beginTime = startTimeThreadLocal.get();
				//请求结束时间
				long endTime = System.currentTimeMillis();
				
				String userAgent = request.getHeader("User-Agent");
				
				
				//客户端访问IP地址
				String ip = this.getIpAddr(request);
				
				//客户端访问类型(1=web，2=android，3=ios，4=weixin)
				String clientType = this.getClientType(request);
				
				String requestParams = this.getRequestParams(request);
				
				//操作结果和异常描述
				Object[] operaResult = this.getOperaResult(request, response);
				
				String userId = this.getCurrentUserId(request, response);
				
				//记录日志详情
				PageData obj = new PageData();
				obj.put("id", UuidUtil.getUUID());
				obj.put("userId", userId);
				obj.put("ip", ip);
				obj.put("beginTime", new Date(beginTime));
				obj.put("endTime", new Date(endTime));
				obj.put("useTime", (endTime-beginTime));
				obj.put("urlId", urlId);
				obj.put("clientType", clientType);
				obj.put("systemVersion", userAgent);
				obj.put("logType", "Z005002");
				obj.put("operaResult", operaResult[0]);
				obj.put("exceptionMsg", operaResult[1]);
				obj.put("exceptionType", operaResult[2]);
				obj.put("requestParams", requestParams);
				obj.put("createdDt", DateUtil.getCurrentDateTime());
				obj.put("createdUserId", userId);
				
				//日志类型（Z005001=登陆日志，Z005002=操作日志）
				if(request.getRequestURI().endsWith("/init/login")){ 
					obj.put("logType", "Z005001");
				}
				
				logService.save(obj);
			}
			
		} catch (Exception e) {
			log.error("保存操作日志异常",e);
		}
		log.debug("===================结束请求 "+ request.getRequestURL());
	}
	
	private String getCurrentUserId(HttpServletRequest request, HttpServletResponse response) {
		if(request.getAttribute(Const.SESSION_LOGIN_USER) != null) {
			return ((PageData)request.getAttribute(Const.SESSION_LOGIN_USER)).getString("id");
		}
		if(StringUtils.isNotBlank(request.getHeader(Const.JWT_TOKEN_NAME))) {
			return JwtUtil.getUserId(request.getHeader(Const.JWT_TOKEN_NAME));
		}
		return null;
	}
  
	
	/**
	 * 请求参数
	 * @author: 徐益森
	 * @date: 2018年4月21日 上午9:24:35
	 * @param {}
	 * @return String
	 */
	private String getRequestParams(HttpServletRequest request) {
		PageData requestParams = new PageData(request);
		String params = null;
		if(requestParams != null && requestParams.size() > 0) {
			params = requestParams.toString();
			if(StringUtils.isNotBlank(params) && params.length() > 2000) {
				params = params.substring(0,2000);
			}
		}
		return params;
	}
	
	/**
	 * 操作结果
	 * @author: 徐益森
	 * @date: 2018年4月20日 上午11:14:21
	 * @param request
	 * @return String[0]操作结果(Y、N) String[1]异常描述
	 */
	private Object[] getOperaResult(HttpServletRequest request, HttpServletResponse response){
		Object[] array = new Object[3];
		//操作结果(Y=成功，N=失败)，默认成功,如果发现异常，则为失败,并记录异常原因
		array[0] = "Z006001";
		Object exception = request.getAttribute(Const.HEADER_ERROR);
		if(exception != null){
			array[0] = "Z006002";
			
			Exception e = (Exception) exception;
			
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw,true));
			String str = sw.toString();
			array[1] = str;
			array[2] = "Z007001";
			
			//业务异常处理
			if(e.getClass().getName().endsWith("BizException")){
				array[1] = e.getMessage();
				array[2] = "Z007002";
			}
		}
		return array;
	}
	
	
	/**
	 * 客户端IP
	 * @author: 徐益森
	 * @date: 2018年4月20日 上午11:14:11
	 * @param {}
	 * @return String
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){  
                //根据网卡取本机配置的IP  
                InetAddress inet=null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                }  
                ip= inet.getHostAddress();  
            }
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15  
            if(ip.indexOf(",")>0){  
            	ip = ip.substring(0,ip.indexOf(","));  
            }  
        }
		return ip;
	}
	
	/**
	 * 客户端访问类型(1=web，2=android，3=ios)
	 * @author: 徐益森
	 * @date: 2018年4月20日 上午11:13:34
	 * @param {}
	 * @return int
	 */
	private String getClientType(HttpServletRequest request){
		//客户端访问类型(1=web，2=android，3=ios)
		String clientType = "Z004001";
		if(StringUtils.isNotBlank(request.getHeader(Const.HEADER_PLATFORM))) {
			String platform = request.getHeader(Const.HEADER_PLATFORM);
			if("android".equals(platform)) {
				clientType = "Z004002";
			}else if("ios".equals(platform)) {
				clientType = "Z004003";
			}else if("android-h5".equals(platform)) {
				clientType = "Z004004";
			}else if("ios-h5".equals(platform)) {
				clientType = "Z004005";
			}
		}
		return clientType;
	}
	
	private String getUrlId(HttpServletRequest request){
		String url = request.getRequestURI();
		if(!URL_MAP.containsKey(url)) {
			PageData data = logService.findUrl(url);
			if(data != null && data.size() > 0) {
				URL_MAP.put(data.getString("url"), data.getString("id"));
			}
		}
		return URL_MAP.get(url);
	}
	
}
