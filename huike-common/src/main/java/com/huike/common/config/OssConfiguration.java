package com.huike.common.config;


import com.huike.common.properties.AliOssProperties;
import com.huike.common.utils.file.AliOssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-06-24 11:15
 */
@Configuration
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean  //如果spring容器里面没有AliOssUtil对象，就将当前方法返回值对象加入spring容器
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }

}
