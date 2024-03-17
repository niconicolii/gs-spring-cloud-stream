package com.example.stream.nameprocessor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;

import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;
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
	public Function<String, List<Message<Map<LocalDateTime, Double>>>> processXmlData() {
		return xmlString -> {
			// 储存本次收到的xml里的所有<Timestamp，demand>；用LinkedHashMap可以保留数据原本的排序
			List<Message<Map<LocalDateTime, Double>>> messages = new ArrayList<>();
//			Map<LocalDateTime, String> timeToString = new LinkedHashMap<>();
//			System.out.println(xmlString);
			try {
				// Parse XML String using JAVA DOM
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;
				builder = factory.newDocumentBuilder();

				// parse得出以DOM tree结构代表的XML数据；
				// 常用methods：.getDocumentElement() 取DOM的child node；
				// 			   .getElementsByTagName("<tagName>") 用xml的标签名找list of values
				//					-> return type = NodeList, 用.time(int)选择，再用.getTextContent()取value
				Document document = builder.parse(new InputSource(new StringReader(xmlString)));
				// Normalize the XML Structure; 格式化DOM tree -> search的时候更可预测+易于编程
				document.getDocumentElement().normalize();
				// 格式化后重新取xml tree的root；常用的properties/method: root.getNodeName()
				Element root = document.getDocumentElement();

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
									Double value = Double.valueOf(demandValue);
									LocalDateTime demandTimestamp = startDateTime.plusMinutes(5*count);
									// 代表一组 timestamp - value 的map
									Map<LocalDateTime, Double> currData = new HashMap<>();
									currData.put(demandTimestamp, value);
									// 把map封装到 message里
									Message<Map<LocalDateTime, Double>> message = MessageBuilder.withPayload(currData).build();
									messages.add(message);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return messages;
		};
	}
}
