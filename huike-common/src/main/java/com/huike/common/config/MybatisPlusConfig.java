package com.huike.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 如果需要使用到mybatis-plus的分页功能，必须存在一个配置类
 * 该配置类创建Mybatis的拦截器，这个拦截器的作用就是在你执行selectPage的方法的时候
 * 对sql进行拦截，然后拼接limit语句实现分页。
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor createMybatisPlusInterceptor(){
        //1. 创建Mybatisplus拦截器
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //2. 往拦截器中添加分页拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        //3 .返回
        return mybatisPlusInterceptor;
    }


}