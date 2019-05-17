package io.goooler.pisciculturemanager.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class OverallDataBean {

    /**
     * @description 首页数据总览，所有的水质参数
     * @id 自增长主键
     * @timeStamp 时间戳
     * @temprature 温度
     * @oxygen 氧含量
     * @ph 酸碱度
     * @nitrogen 氨氮含量
     * @nitrite 亚硝酸盐含量
     */

    @Id(autoincrement = true)
    private long id;
    private long timeStamp;
    private double oxygen;
    private double temperature;
    private double ph;
    private double nitrogen;
    private double nitrite;

    public OverallDataBean() {
    }

    @Generated(hash = 1296242961)
    public OverallDataBean(long id, long timeStamp, double oxygen,
                           double temperature, double ph, double nitrogen, double nitrite) {
        this.id = id;
        this.timeStamp = timeStamp;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
