package com.ams.common.config;

import com.ams.common.interceptor.LogMDCInterceptor;
import com.ams.common.interceptor.RequestLogHandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Resource
    private LogMDCInterceptor logMDCInterceptor;
    
    @Resource
    private RequestLogHandlerInterceptor logHandlerInterceptor;
    
//    @Value("${ams.file.max-upload-size}")
//    private Integer maxUploadSize;
//    
//    @Value("${ams.file.max-in-memory-size}")
//    private Integer maxInMemorySize;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logMDCInterceptor)
                .excludePathPatterns(Arrays.asList("/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**", "/error"));
        
        registry.addInterceptor(logHandlerInterceptor)
                .excludePathPatterns(Arrays.asList("/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**", "/error"));
    }
    
//    @Bean(name = "multipartResolver")
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        // 设定默认编码
//        resolver.setDefaultEncoding("utf-8");
//        // 设定延迟文件解析
//        resolver.setResolveLazily(true);
//        // 设定文件上传时写入内存的最大值，如果小于这个参数就不会生成临时文件，默认为10240
//        resolver.setMaxInMemorySize(maxInMemorySize);
//        // 上传文件的最大值为100M
//        resolver.setMaxUploadSize(maxUploadSize);
//        return resolver;
//    }
    
}
