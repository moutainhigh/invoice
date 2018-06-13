package com.ztessc.einvoice.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration  
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	//此处必须要创建bean，否则LogInterceptor无法注入bean
	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}
	
	//此处必须要创建bean，否则signatureValidInterceptor无法注入bean
	@Bean
	public SignatureValidInterceptor signatureValidInterceptor() {
		return new SignatureValidInterceptor();
	}
	
	@Bean
	public AppSingleDeviceLoginInterceptor appSingleDeviceLoginInterceptor() {
		return new AppSingleDeviceLoginInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//这里可以添加多个拦截器 
		registry.addInterceptor(appSingleDeviceLoginInterceptor()).addPathPatterns("/app/**").excludePathPatterns("/error");
		registry.addInterceptor(logInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
		registry.addInterceptor(signatureValidInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/error");
		super.addInterceptors(registry);
	}
	
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		//创建FastJson信息转换对象
//		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//		//创建Fastjosn对象并设定序列化规则
//		FastJsonConfig config = new FastJsonConfig();
//		config.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty,
//				SerializerFeature.WriteMapNullValue,
//				SerializerFeature.WriteNullStringAsEmpty,
//				SerializerFeature.WriteNullBooleanAsFalse,
//				SerializerFeature.WriteDateUseDateFormat,
//				SerializerFeature.PrettyFormat);
//		// 中文乱码解决方案
//        List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);//设定json格式且编码为UTF-8
//        converter.setSupportedMediaTypes(mediaTypes);
//		//规则赋予转换对象
//		converter.setFastJsonConfig(config);
//		converters.add(converter);
//	}
}
	
