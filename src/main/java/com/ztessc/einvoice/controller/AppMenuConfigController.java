package com.ztessc.einvoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.service.AppMenuConfigService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RequestMapping(value = "/config/menu")
@RestController
public class AppMenuConfigController extends BaseController {

	@Autowired
	private AppMenuConfigService appMenuConfigService;
	
	/**
	 * 获取所有数据
	 * @author: 徐益森
	 * @date: 2018年4月11日 下午6:19:59
	 * @return PageData
	 */
	@PostMapping(value = "/list")
    public PageData list() {
    	List<PageData> list = appMenuConfigService.listMenuConfigs();
        return MessageUtil.getSuccessMessage(list);
    }
	
	/**
     * 修改
     * @author: 徐益森
     * @date: 2018年4月11日 下午6:22:26
     * @param {"id":"aaa","imgName":"票联系统","imgDesc":"XXX主题","fileName":"xxxx.png","realName":"xxxx.png","enabled":"Z001002","sort":""}
     * @return PageData
     */
	@PostMapping(value = "/update")
	public PageData update() {
		PageData params = this.getPageData();
		appMenuConfigService.update(params);
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_UPDATE);
	}
	
}
