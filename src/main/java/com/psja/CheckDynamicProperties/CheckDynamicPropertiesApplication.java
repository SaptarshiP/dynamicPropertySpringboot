package com.psja.CheckDynamicProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.psja.CheckDynamicProperties.config.DynamicPropertyLoadConfig;

@SpringBootApplication
public class CheckDynamicPropertiesApplication {

	public static void main(String[] args) {
		//SpringApplication.run(CheckDynamicPropertiesApplication.class, args);
		SpringApplication sp = new SpringApplication(CheckDynamicPropertiesApplication.class);
		sp.addInitializers( new DynamicPropertyLoadConfig() );
		sp.run(args);
	}

}
