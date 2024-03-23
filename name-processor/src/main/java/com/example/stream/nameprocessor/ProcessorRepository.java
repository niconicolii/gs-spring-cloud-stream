package com.example.stream.nameprocessor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessorRepository extends MongoRepository<DemandData, String> {

//    @Query(sort = "{ 'timestamp' : -1 }")
    Optional<DemandData> findTopByOrderByTimestampDesc();

}
