package com.example.stream.namesink;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import com.example.stream.namesink.DemandData;

@Configuration
public class NameSinkConfiguration {
    @Autowired
    private NameSinkRepository repository;

    @Bean
    public Consumer<Message<Map<LocalDateTime, Double>>> nameSink() {
        return timeToValue -> {
            Map<LocalDateTime, Double> demandData = (Map<LocalDateTime, Double>) timeToValue.getPayload();
//            System.out.println("Received map: " + demandData);
            for (LocalDateTime timestamp : demandData.keySet()) {
                System.out.println("Received map, timestamp: " + timestamp +
                        ", value: " + demandData.get(timestamp));
                Optional<DemandData> optional = repository.findDemandDataByTimestamp(timestamp);
                if (repository.findDemandDataByTimestamp(timestamp).isEmpty()){
                    System.out.println("Didn't find any duplicate record");
//                if (optional.isEmpty()) {
                    DemandData newDemandData = new DemandData(
                            timestamp,
                            demandData.get(timestamp)
                            );
                    repository.insert(newDemandData);
                    System.out.println("Sent to DB");
                }
            }
        };
    }
}
