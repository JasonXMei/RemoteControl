package com.jason.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

/**
 * MybatisPlus配置文件
 */
@Configuration
@MapperScan("com.jason.web.mapper")
public class MybatisPlusConfig {

    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        //启用性能分析插件
        return new PerformanceInterceptor();
    }

}
