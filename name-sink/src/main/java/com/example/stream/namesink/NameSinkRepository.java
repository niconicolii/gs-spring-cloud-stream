package com.example.stream.namesink;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NameSinkRepository
        extends MongoRepository<DemandData, String> {

    Optional<DemandData> findDemandDataByTimestamp(LocalDateTime timestamp);
}
