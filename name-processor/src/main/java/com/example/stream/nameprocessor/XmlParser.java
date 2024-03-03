package com.example.stream.nameprocessor;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;

public class XmlParser {
    public static Root parseXmlString(String xmlString) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Root.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Root) unmarshaller.unmarshal(new StringReader(xmlString));
    }

    public static void main(String[] args) {
        String xmlString = "Your XML String Here";
        try {
            Root root = parseXmlString(xmlString);
            DataSet actualDataSet = root.getActualDataSet();
            if (actualDataSet != null) {
                System.out.println("DataSet Series=\"Actual\":");
                for (Data data : actualDataSet.getData()) {
                    System.out.println(data.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

