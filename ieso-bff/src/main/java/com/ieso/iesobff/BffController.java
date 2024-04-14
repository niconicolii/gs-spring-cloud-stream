package com.ieso.iesobff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class BffController {
    @Autowired
    private BffService bffService;

    @GetMapping
    public List<DemandData> getDemandDataList(
            @RequestParam(name="startTime", required=false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(name = "endTime", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        if (startTime != null && endTime != null) {
            return bffService.getDemandDataBetweenRange(startTime, endTime);
        } else {
            return bffService.getAll();
        }
    }
}
