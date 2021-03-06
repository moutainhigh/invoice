package com.ztessc.einvoice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.cache.LogoutCache;
import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.service.SysRoleService;
import com.ztessc.einvoice.service.UserService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.JwtUtil;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.MD5;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RestController
public class LoginController extends BaseController {

	private static final Logger log = Log.get();
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private UserService userService;
	@Autowired
	private LogoutCache logoutCache;
	
	/**
     * 登录方法
     * @param {"username":"admin","password":"888888"}
     * @return
     */
	@PostMapping(value = "/init/login")
    public PageData login(HttpServletRequest request, HttpServletResponse response) {
		PageData params = this.getPageData();
        PageData result = new PageData();
    	PageData user = userService.findBy(params);
    	if(user == null || !user.getString("password").equals(MD5.md5(params.getString("password")))) {
    		throw new BizException("用户名或密码错误", Const.ERROR_CODE_USERNAME_PASSWORD_ERROR);
    	}
    	
        result.put("user", user);
        String token = JwtUtil.sign(user);
    	result.put(Const.JWT_TOKEN_NAME, token);
        
        request.setAttribute(Const.SESSION_LOGIN_USER, user);
        
        return MessageUtil.getSuccessMessage(result, "登录成功");
    }
	
    /**
	 * 用户注销
	 */
	@RequestMapping(value = "/init/logout")
	public PageData logout(HttpServletRequest request) {
		try {
			String token = request.getHeader(Const.JWT_TOKEN_NAME);
			long expiresTime = JwtUtil.getExpiresTime(token);
			long currentTime = System.currentTimeMillis();
			
			logoutCache.cache(token, (expiresTime - currentTime) / 1000);
		} catch (Exception e) {
			log.error("注销缓存失败", e);
		}
		
		return MessageUtil.getSuccessMessage("注销成功");
	}
	
	
	@RequestMapping(value = "/init/unlogin")
    public PageData unlogin() {
        return MessageUtil.getErrorMessage("未登录或登录失效,请重新登录！",Const.ERROR_CODE_UNLOGIN);
    }
	
	/**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     */
    @RequestMapping(value = "/init/unauth")
    public PageData unauth() {
        return MessageUtil.getErrorMessage("没权限",Const.ERROR_CODE_UNAUTH);
    }
}
