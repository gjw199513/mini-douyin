package com.gjw;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gjw19 on 2018/6/17.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     *
     * @param registry
     */
    // 将本地文件映射为服务器资源文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 为swagger加入访问路径
                .addResourceLocations("classpath:/META-INF/resources/")
                // 为图片加入访问路径
                .addResourceLocations("file:G:/java程序/mini-douyin/upload/");
    }
}
