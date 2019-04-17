package com.jason.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.jason.web.mapper")
public class WuWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WuWebApplication.class, args);
	}

}
