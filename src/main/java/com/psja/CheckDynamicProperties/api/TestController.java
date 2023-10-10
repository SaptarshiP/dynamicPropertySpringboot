package com.psja.CheckDynamicProperties.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class TestController {

	@Value("${test.firstproperty}")
	private String value;
	
	@GetMapping(value="getData")
	public String getData() {
		System.out.println(value);
		return value;
	}
	
}
