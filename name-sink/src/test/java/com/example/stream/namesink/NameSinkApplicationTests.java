package com.example.stream.namesink;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NameSinkApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testConnection() {
        // Attempt to use the MongoTemplate to perform any operation, for example, count documents in a collection
        long count = mongoTemplate.getCollection("yourCollectionName").countDocuments();

        // Just asserting the operation succeeds is a sign of a successful connection
        assertThat(count).isGreaterThanOrEqualTo(0); // Adjust based on your expectations
    }
}