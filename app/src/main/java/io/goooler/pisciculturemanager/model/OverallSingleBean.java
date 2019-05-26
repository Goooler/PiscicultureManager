package io.goooler.pisciculturemanager.model;

import io.goooler.pisciculturemanager.util.DateUtil;

/**
 * 单一的指标对应唯一值，用于总览各项数据时在列表中的展示
 */

public class OverallSingleBean {
    private String name;
    private double value;
    private long timestamp;

    public OverallSingleBean(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public OverallSingleBean(String name, double value, long timestamp) {
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public String getValueString() {
        return value + Constants.NULL_STRING;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return DateUtil.timestampToDate(timestamp);
    }
}
