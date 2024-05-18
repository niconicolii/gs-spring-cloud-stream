package com.example.stream.namesink;

import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface DemandMapper {
    @Select("SELECT * FROM demandData")
    List<DemandData> findAllDemand();

    @Insert("INSERT INTO demandData(timestamp, value) VALUES(#{timestamp}, #{value})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDemand(DemandData demandData);

    @Select("SELECT * from demandData WHERE timestamp = #{timestamp}")
    DemandData findDemandDataByTimestamp(@Param("timestamp") Timestamp timestamp);
}
