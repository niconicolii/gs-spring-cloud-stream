package com.ieso.iesobff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BffService {
    @Autowired
    private BffRepository bffRepository;


    public List<DemandData> getDemandDataBetweenRange(
            LocalDateTime startTime, LocalDateTime endTime) {
        return bffRepository.findByTimestampBetween(startTime, endTime);
    }

    public List<DemandData> getAll() {
        return bffRepository.findAll();
    }
}
