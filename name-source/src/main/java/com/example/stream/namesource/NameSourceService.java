package com.example.stream.namesource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


// 用rest template 从 IESO 下载xml文件
@Service
public class NameSourceService {

    private final RestTemplate restTemplate;

    public NameSourceService() {
        this.restTemplate = new RestTemplate();
    }

    public String getXmlFromIESO() {
        String url = "https://www.ieso.ca/-/media/Files/IESO/uploaded/Chart/ontario_demand_multiday.xml";
        return restTemplate.getForObject(url, String.class);
    }
}
