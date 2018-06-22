package com.gjw;

import com.gjw.controller.intercept.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gjw19 on 2018/6/17.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    // 将本地文件映射为服务器资源文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 为swagger加入访问路径
                .addResourceLocations("classpath:/META-INF/resources/")
                // 为图片加入访问路径
                .addResourceLocations("file:D:/java程序/mini-douyin/upload/");
    }

    @Bean(initMethod = "init")
    public ZKCuratorClient zkCuratorClient() {
        return new ZKCuratorClient();
    }

    // 在spring中，对拦截器进行注册
    @Bean
    public MiniInterceptor miniInterceptor(){
        return new MiniInterceptor();
    }

    // 把其注入拦截器中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 将拦截器进行注册
        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
                                .addPathPatterns("/video/upload","/video/uplaodCover",
                                        "/video/userLike", "/video/userUnLike", "/video/saveComment")
                                                    .addPathPatterns("/bgm/**")
                                                    .excludePathPatterns("/user/queryPublisher");
        super.addInterceptors(registry);
    }
}
