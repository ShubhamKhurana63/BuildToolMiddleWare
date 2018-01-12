package com.buildtool.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.jcraft.jsch.JSch;

@Configuration
@ComponentScan(basePackages = "com.buildtool.*")
@EnableAspectJAutoProxy
public class BeanConfig {

	@Bean
	public JSch getJsch() {
		return new JSch();
	}

}
