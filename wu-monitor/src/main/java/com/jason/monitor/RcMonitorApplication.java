package com.jason.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
@EnableAutoConfiguration
public class RcMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcMonitorApplication.class, args);
	}

}
