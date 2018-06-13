package com.ztessc.einvoice.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class ShiroUtil {
	
	public static PageData getCurrentUserData(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return JwtUtil.getUserInfo(request.getHeader(Const.JWT_TOKEN_NAME));
	}
	
	public static String  getCurrentUserId(){
		return getCurrentUserData().getString("id");
	}
	
}
