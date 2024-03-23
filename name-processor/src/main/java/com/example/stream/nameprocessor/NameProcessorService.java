package com.example.stream.nameprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NameProcessorService {
    @Autowired
    private ProcessorRepository processorRepository;

    public LocalDateTime getMaxDateTime() {
        LocalDateTime maxDateTime = LocalDateTime.MIN;
        Optional<DemandData> optional = processorRepository.findTopByOrderByTimestampDesc();
        if (optional.isPresent()) {
            maxDateTime = optional.get().getTimestamp();
        }
        return maxDateTime;
    }
}
