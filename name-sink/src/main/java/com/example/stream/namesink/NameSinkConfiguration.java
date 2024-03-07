package com.example.stream.namesink;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NameSinkConfiguration {

    @Bean
    public Consumer<Map<LocalDateTime, String>> nameSink() {
        return timeToValue -> {
            timeToValue.forEach((dateTime, value) -> {
                System.out.println("Timestamp : " + dateTime +
                        ", Value : " + value);
            });
        };
    }
}
