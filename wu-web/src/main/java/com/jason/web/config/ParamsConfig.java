package com.jason.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParamsConfig {

	@Value("${file.upload-path}")
	public String imagePath;

    @Value("${dafault-password}")
    public String defaultPassword;

    @Value("${default.session.timeout}")
    public Integer defaultSessionTimeout;
}
