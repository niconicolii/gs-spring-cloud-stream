package com.example.stream.nameprocessor;

import java.util.Date;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NameProcessorConfiguration {

	@Bean
	public Function<String, String> processXmlData() {
		return xmlData -> {
			System.out.println(xmlData);
			return "Got XML data hehehehheheheheheheh";
		};
	}
}
