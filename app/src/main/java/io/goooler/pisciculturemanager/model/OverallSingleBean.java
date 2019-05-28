package io.goooler.pisciculturemanager.model;

/**
 * 单一的指标对应唯一值，用于总览各项数据时在列表中的展示
 */

public class OverallSingleBean {
    private String name;
    private double value;

    /**
     * @param name  单值名字
     * @param value 单值
     */
    public OverallSingleBean(String name, double value) {
        this.name = name;
        this.value = value;
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
}
