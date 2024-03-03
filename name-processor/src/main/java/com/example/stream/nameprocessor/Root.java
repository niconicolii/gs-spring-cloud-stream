package com.example.stream.nameprocessor;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {

    @XmlElement(name = "StartDate")
    private String startDate;

    @XmlElement(name = "DataSet")
    private List<DataSet> dataSets;

    // Getters and setters

    public DataSet getActualDataSet() {
        if (dataSets != null) {
            for (DataSet dataSet : dataSets) {
                if ("Actual".equals(dataSet.getSeries())) {
                    return dataSet;
                }
            }
        }
        return null; // Or an empty DataSet instance
    }

    public String getStartDate() {
        return startDate;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class DataSet {
    @XmlAttribute(name = "Series")
    private String series;

    @XmlElement(name = "Data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public String getSeries() {
        return series;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Data {
    @XmlElement(name = "Value")
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
