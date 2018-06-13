package com.ztessc.einvoice.invoicecheck.hante;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.UuidUtil;

public class Check {

	/**查验提交接口*/
	private static String SUBMIT_URL = "http://www.taxdata.com.cn/base/1023/httpService";
	/**查验查询接口*/
	private static String SEARCH_URL = "http://www.taxdata.com.cn/base/1024/httpService";
	
	public static void submit() {
//		PageData data = new PageData();
//		data.put("fapdm", "044001500111");
//		data.put("faphm", "91398776");
//		data.put("faplx", "026");
////		data.put("jine", "415.57");
//		data.put("kaiprq", "20180111");
//		data.put("waibid", System.currentTimeMillis());
//		data.put("xiaoym", "14676717298231023492");
		
		Map<String, String> params = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
		params.put("request_id", UuidUtil.getUUID());
		params.put("request_time", "20180508165059");
		params.put("application", "91440300MA5EXWHW6FTT001");
		params.put("data", "{\"fapdm\":\"044001500111\",\"faphm:\"91398776\",\"faplx\":\"026\",\"jine\":\"415.57\",\"kaiprq\":\"20180111\",\"waibid\":\"1525768830046\",\"xiaoym\":\"14676717298231023492\"}");
		params.put("qiybh", "91440300MA5EXWHW6FTT001");
		params.put("sign", "3b3918a3120167512eca30328c987d20");
		
		String result = HttpClientUtil.post(SUBMIT_URL, params);
		System.out.println("submit---result====" + result);
	}
	
	public static void search() {
		PageData data = new PageData();
		data.put("fapdm", "044001500111");
		data.put("faphm", "91398776");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("request_id", UuidUtil.getUUID());
		params.put("request_time", "20180504171638");
		params.put("application", "91440300MA5EXWHW6FTT001");
		params.put("data", data.toString());
		params.put("qiybh", "91440300MA5EXWHW6FTT001");
		params.put("sign", "aae20c39100dbbb8f407b32aa90badc4");
		String result = HttpClientUtil.post(SEARCH_URL, params );
		System.out.println("search---result====" + result);
	}
	
	public static void main(String[] args) throws Exception {
		submit();
	}
}
