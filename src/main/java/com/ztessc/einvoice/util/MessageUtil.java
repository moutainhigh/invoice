package com.ztessc.einvoice.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;



public class MessageUtil {
	
	public static PageData getSuccessMessage(){
		return getSuccessMessage(null,null,null);
	}
	
	public static PageData getSuccessMessage(PageData pd){
		return getSuccessMessage(pd, null, null);
	}
	
	public static PageData getSuccessMessage(String message){
		return getSuccessMessage(null, null, message);
	}
	
	public static PageData getSuccessMessage(PageData pd, String message){
		return getSuccessMessage(pd, null, message);
	}
	
	public static PageData getSuccessMessage(List<PageData> list){
		return getSuccessMessage(null, list, null);
	}
	
	public static PageData getSuccessMessage(PageData pd, List<PageData> list, String message){
		if(pd == null){
			pd = new PageData();
		}
		pd.put(Const.MESSAGE_OK, true);

		if(list != null) {
			pd.put(Const.MESSAGE_LIST, list);
		}
		if(StringUtils.isNotBlank(message)) {
			pd.put(Const.MESSAGE, message);
		}
		return pd;
	}
	
	public static PageData getErrorMessage(PageData pd){
		return getErrorMessage(pd, null, null);
	}
	
	public static PageData getErrorMessage(String errorMessage){
		return getErrorMessage(null, errorMessage, null);
	}
	
	public static PageData getErrorMessage(String errorMessage, String code){
		return getErrorMessage(null, errorMessage, code);
	}
	
	public static PageData getErrorMessage(PageData pd, String errorMessage, String code){
		if(pd == null){
			pd = new PageData();
		}
		pd.put(Const.MESSAGE_OK, false);
		
		if(StringUtils.isNotBlank(errorMessage)) {
			pd.put(Const.MESSAGE, errorMessage);
		}
		if(StringUtils.isNotBlank(code)) {
			pd.put(Const.MESSAGE_CODE, code);
		}
		return pd;
	}
	
	
}
