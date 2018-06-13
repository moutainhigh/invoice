package com.ztessc.einvoice.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Const {

	/**返回提示信息:message**/
	public static final String MESSAGE = "message";
	/**返回提示信息:code**/
	public static final String MESSAGE_CODE = "code";
	/**返回提示信息:list**/
	public static final String MESSAGE_LIST = "list";
	/**返回成功提示信息:ok**/
	public static final String MESSAGE_OK = "ok";
	/**返回成功提示信息:保存成功**/
	public static final String MESSAGE_SUCCESS_SAVE = "保存成功";
	/**返回成功提示信息:修改成功**/
	public static final String MESSAGE_SUCCESS_UPDATE = "修改成功";
	/**返回成功提示信息:删除成功**/
	public static final String MESSAGE_SUCCESS_DELETE = "删除成功";
	
	public static final String SESSION_LOGIN_USER = "loginUser";
	
	public static final String DEFAULT_CHARSET_NAME = "UTF-8";
	
	public static final String JWT_TOKEN_NAME = "Authorization";
	
	public static final String HEADER_ERROR = "X-tjmjApp-error";
	
	public static final String HEADER_ERROR_BIZ = "X-tjmjApp-error-biz";
	
	public static final String HEADER_DEVICE_TOKEN = "Device-Token";
	
	public static final String HEADER_PLATFORM = "Platform";
	

	/**文件上传目录*/
	public static final Map<String, String> FILE_UPLOAD_DIR = new HashMap<String, String>(){{
		/**启动页*/
		put("1001", "boot");
		/**logo*/
		put("1002", "logo");
		/**轮播图*/
		put("1003", "slideshow");
		/**栏目*/
		put("1004", "button");
		/**菜单*/
		put("1005", "menu");
		/**发票*/
		put("1006", "invoice");
	}};
	
	/**状态 ------(启用/停用)**/
	public static final String DIC_ENABLED_TYPE = "Z001000";
	/**状态------启用**/
	public static final String DIC_ENABLED_TYPE_QY = "Z001001";
	/**状态------停用**/
	public static final String DIC_ENABLED_TYPE_TY = "Z001002";
	
	/**验证状态 ------(未验证/已验证/验证失败)**/
	public static final String DIC_VALID_STATE_TYPE = "Z002000";
	/**验证状态------未验证**/
	public static final String DIC_VALID_STATE_TYPE_WYZ = "Z002001";
	/**验证状态------已验证**/
	public static final String DIC_VALID_STATE_TYPE_YYZ = "Z002002";
	/**验证状态------验证失败**/
	public static final String DIC_VALID_STATE_TYPE_YYSB = "Z002003";
	
	/**发票来源 ------(PDF上传/微信卡包获取/手工录入)**/
	public static final String DIC_INVOICE_SOURCE_TYPE = "Z003000";
	/**发票来源------PDF上传**/
	public static final String DIC_INVOICE_SOURCE_TYPE_PDF = "Z003001";
	/**发票来源------微信卡包获取**/
	public static final String DIC_INVOICE_SOURCE_TYPE_WEIXIN = "Z003002";
	/**发票来源------手工录入**/
	public static final String DIC_INVOICE_SOURCE_TYPE_HAND = "Z003003";
	/**发票来源------手工扫描**/
	public static final String DIC_INVOICE_SOURCE_TYPE_HAND_SCAN = "Z003004";
	
	/**错误代码-----未登录或帐号已失效*/
	public static final String ERROR_CODE_UNLOGIN = "10000";
	/**错误代码-----未授权*/
	public static final String ERROR_CODE_UNAUTH = "10001";
	/**错误代码-----用户名或密码错误*/
	public static final String ERROR_CODE_USERNAME_PASSWORD_ERROR = "10002";
	/**错误代码-----需要版本版本升级*/
	public static final String ERROR_CODE_UPGRADE_VERSION = "10003";
	/**错误代码-----资源不存在*/
	public static final String ERROR_CODE_PAGE_NOT_FOUND = "10004";
	
}
