package com.example.stream.namesource;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NameSourceConfiguration {

	@Autowired
	private NameSourceService nameSourceService;

	// retrieve xml file from IESO
	@Bean
	public Supplier<String> supplyName() {
		return () -> {
			String xmlData = nameSourceService.getXmlFromIESO();
//			System.out.println("==================================================");
//			System.out.println(xmlData);
			return xmlData;
		};
	}
}
