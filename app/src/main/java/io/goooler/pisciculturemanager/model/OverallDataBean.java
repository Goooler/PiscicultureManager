package io.goooler.pisciculturemanager.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class OverallDataBean {

    @Id(autoincrement = true)
    private long id;
    private long timestamp;
    private double oxygen;
    private double temperature;
    private double ph;
    private double nitrogen;
    private double nitrite;

    public OverallDataBean() {
    }

    public OverallDataBean(long timestamp, double oxygen, double temperature, double ph, double nitrogen, double nitrite) {
        this.timestamp = timestamp;
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.ph = ph;
        this.nitrogen = nitrogen;
        this.nitrite = nitrite;
    }

    @Generated(hash = 760494190)
    public OverallDataBean(long id, long timestamp, double oxygen, double temperature, double ph, double nitrogen,
                           double nitrite) {
        this.id = id;
        this.timestamp = timestamp;
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.ph = ph;
        this.nitrogen = nitrogen;
        this.nitrite = nitrite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public double getNitrite() {
        return nitrite;
    }

    public void setNitrite(double nitrite) {
        this.nitrite = nitrite;
    }
}
