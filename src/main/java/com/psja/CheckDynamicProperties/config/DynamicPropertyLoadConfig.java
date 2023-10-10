package com.psja.CheckDynamicProperties.config;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class DynamicPropertyLoadConfig implements ApplicationContextInitializer<ConfigurableApplicationContext>{
	
	@Override
	public void initialize( ConfigurableApplicationContext applicationContext ) {
		ConfigurableEnvironment configEnv = (ConfigurableEnvironment)applicationContext.getEnvironment();
		
		MutablePropertySources configPropertySource = configEnv.getPropertySources();
		PropertySource<?> configProperty = configPropertySource.get("Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'");
		System.out.println(configProperty.getProperty("server.port"));
		
        applicationContext.getEnvironment().getPropertySources().addLast(new MapPropertySource("Database_properties", getNewProperties(configProperty)));

	}
	
	private Map<String, Object> getNewProperties( PropertySource<?> configProperty ) {
		Map<String, Object> propertyMap = new HashMap<>();
		try {
			Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
		} catch(Exception exp) {
			System.out.println(exp.getMessage());
		}
		try(Connection conn = DriverManager.getConnection(configProperty.getProperty("spring.datasource.url").toString(), "root", "root");
				PreparedStatement ps = conn.prepareStatement("select name,value from properties")) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				propertyMap.put(rs.getString("name"), rs.getString("value"));
			}
		}catch(Exception exp) {
			System.out.println(exp.getMessage());
		}
		return propertyMap;
		
	}

}
