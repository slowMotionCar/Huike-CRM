package com.huike.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.huike.common.config.HuiKeConfig;
import com.huike.common.constant.Constants;
import com.huike.framework.interceptor.RepeatSubmitInterceptor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;

/**
 * 通用配置
 * 
 * 
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{
    @Resource
    private RepeatSubmitInterceptor repeatSubmitInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        // /** 本地文件上传路径 */
        // registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + HuiKeConfig.getProfile() + "/");
        //
        // /** swagger配置 */
        // registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket1() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("基础模块接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.system"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket2() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("线索模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.clues"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket3() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("商机模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.business"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket4() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("通用模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.common"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket5() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("合同模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.contract"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket6() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("监控模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.monitor"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket7() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("报表模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.report"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket8() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("汇客CRM项目接口文档")
                .version("2.0")
                .description("汇客CRM项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("规则模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huike.web.controller.rule"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}