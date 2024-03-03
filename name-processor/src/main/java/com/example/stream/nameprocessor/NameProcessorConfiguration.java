package com.example.stream.nameprocessor;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Function;

import org.springframework.amqp.core.Message;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Configuration
public class NameProcessorConfiguration {

	@Bean
	public Function<String, Map<LocalDateTime, String>> processXmlData() {
		return xmlString -> {
			Map<LocalDateTime, String> timeToString = new HashMap<>();
//			System.out.println(xmlString);
			try {
				// Parse the XML String
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;

				builder = factory.newDocumentBuilder();

				Document document = builder.parse(new InputSource(new StringReader(xmlString)));

				// Normalize the XML Structure; It's just too important !!
				document.getDocumentElement().normalize();

				// Here comes the root node
				Element root = document.getDocumentElement();
//				System.out.println(root.getNodeName());

				// Get StartDate
				String startDate = document.getElementsByTagName("StartDate").item(0).getTextContent();
				LocalDateTime startDateTime = LocalDateTime.parse(startDate);
				// Get all DataSet nodes
				NodeList nList = document.getElementsByTagName("DataSet");
//				System.out.println("============================");

				// 因为有3个DataSet所以需要loop
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node node = nList.item(temp);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node;
						// 只留Series=5_Minute的DataSet
						if ("5_Minute".equals(eElement.getAttribute("Series"))) {
//							System.out.println("Series : " + eElement.getAttribute("Series"));
							NodeList dataNodes = eElement.getElementsByTagName("Data");
							// 读取这个DataSet底下的所有Data
							for (int count = 0; count < dataNodes.getLength(); count++) {
								Node dataNode = dataNodes.item(count);
								if (dataNode.getNodeType() == dataNode.ELEMENT_NODE) {
									Element dataElement = (Element) dataNode;
									// 取得Data里面的数据，每个数据间隔5分钟，需要计算出来时间
									String demandValue = dataElement.getElementsByTagName("Value").item(0).getTextContent();
									LocalDateTime demandTimestamp = startDateTime.plusMinutes(5*count);
//									System.out.println("Timestamp : " + demandTimestamp +
//											", Value : " + demandValue);
									timeToString.put(demandTimestamp, demandValue);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return timeToString;
		};
	}
}
