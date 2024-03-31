package com.ieso.iesobff;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BffRepository extends MongoRepository<DemandData, String> {

    @Query(" { 'timestamp': { $gte: ?0, $lte: ?1 } } ")
    List<DemandData> findByTimestampBetween(LocalDateTime startTime,
                                            LocalDateTime endTime);
}
