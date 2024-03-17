package com.example.stream.namesink;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class DemandData {
    @Id
    private String id;
    @Indexed(unique = true)
    private LocalDateTime timestamp;

    public DemandData(LocalDateTime timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    private double value;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
