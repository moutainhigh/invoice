package com.ztessc.einvoice.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ztessc.einvoice.BaseJunit4Test;
import com.ztessc.einvoice.util.PageData;

public class AppBootConfigServiceTest extends BaseJunit4Test{

	@Autowired
	private AppBootConfigService appBootConfigService;
	
//	@Before
//	public void before(){
//		PageData data = new PageData();
//		data.put("id", "ec1337036eda4a85be9f4bec1ae5f821");
//		ShiroUtil.setCurrentUserData(data);
//	}
	
	@Test
	public void testFindBootConfig() {
		PageData pd = new PageData();
		PageData config = appBootConfigService.findBootConfig(pd);
		System.out.println("config-----" + config);
	}
	
	@Test
	public void testSave() {
		PageData pd = new PageData();
		pd.put("imgName", "bbb");
		pd.put("imgDesc", "bbb");
		pd.put("imgPath", "bbb");
		pd.put("enabled", "Y");
		appBootConfigService.save(pd);
	}

	@Test
	public void testUpdate() {
		PageData pd = new PageData();
		pd.put("id", "8a1ec57bfed147e9b4ddb8a16e7204f7");
		pd.put("imgName", "bbb");
		pd.put("imgDesc", "bbb");
		pd.put("imgPath", "bbb");
		pd.put("enabled", "N");
		appBootConfigService.update(pd);
	}

	@Test
	public void testDelete() {
		PageData pd = new PageData();
		pd.put("id", "8a1ec57bfed147e9b4ddb8a16e7204f7");
		appBootConfigService.delete(pd);
	}

}
