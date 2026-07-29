package com.sky.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.sky.interceptor.JwtTokenAdminInterceptor;
import com.sky.interceptor.JwtTokenUserInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee" +
                        "" +
                        "" +
                        "" +
                        "/login");
        log.info("自定义拦截器注册完成...");
        // 注册用户端令牌校验拦截器
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/login")     // 排除登录
                .excludePathPatterns("/user/shop/status");    // 排除获取店铺状态
//                .excludePathPatterns("/user/category/list")  // 👈 新增：排除分类列表（解决401）
//                .excludePathPatterns("/user/dish/list")      // 👈 新增：排除菜品列表（防止后续401）
//                .excludePathPatterns("/user/setmeal/list");  // 👈 新增：排除套餐列表（防止后续401）


    }



    /**
     * 通过 Jackson2ObjectMapperBuilderCustomizer 自定义日期时间序列化格式，
     * 避免替换消息转换器导致 springdoc OpenAPI 序列化异常
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            String datePattern = "yyyy-MM-dd";
            String dateTimePattern = "yyyy-MM-dd HH:mm";
            String timePattern = "HH:mm:ss";
            builder.serializers(
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimePattern)),
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(datePattern)),
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(timePattern))
            );
            builder.deserializers(
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimePattern)),
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(datePattern)),
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timePattern))
            );
        };
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖项目接口文档")
                        .description("苍穹外卖项目接口文档")
                        .version("2.0"));
    }

    /**
     * 管理端接口分组
     */
    @Bean
    public GroupedOpenApi adminApi() {
        log.info("开始生成管理端接口文档...");
        return GroupedOpenApi.builder()
                .group("管理端接口")
                // 指定管理端控制器所在的包
                .packagesToScan("com.sky.controller.admin")
                .build();
    }

    /**
     * 用户端接口分组
     */
    @Bean
    public GroupedOpenApi userApi() {
        log.info("开始生成用户端接口文档...");
        return GroupedOpenApi.builder()
                .group("用户端接口")
                // 指定用户端控制器所在的包
                .packagesToScan("com.sky.controller.user")
                .build();
    }

}
