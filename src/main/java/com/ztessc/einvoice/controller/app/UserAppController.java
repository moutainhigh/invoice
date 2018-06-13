package com.ztessc.einvoice.controller.app;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.controller.BaseController;
import com.ztessc.einvoice.service.UserService;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RequestMapping(value = "/app")
@RestController
public class UserAppController extends BaseController{
	
	@Autowired
	private UserService userService;

	/**
	 * 根据关键字搜索用户(根据工号、姓名模糊查找前5条)
	 * @author: 徐益森
	 * @date: 2018年4月26日 下午3:37:55
	 * @param {"keyword":"张三"}
	 * @return List<PageData>
	 */
	@PostMapping(value = "/v1/user/list")
	public PageData list() {
		PageData params = this.getPageData();
		if(StringUtils.isNotBlank(params.getString("keyword"))) {
			List<PageData> list = userService.listUserByKeyword(params);
			return MessageUtil.getSuccessMessage(list);
		}
		return MessageUtil.getSuccessMessage();
	}
	
}
