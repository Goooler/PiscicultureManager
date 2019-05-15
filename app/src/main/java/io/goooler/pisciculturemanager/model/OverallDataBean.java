package io.goooler.pisciculturemanager.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OverallDataBean {

    /**
     * @description 首页数据总览，所有的水质参数
     *
     * @id 自增长主键
     * @temprature 温度
     * @ph 酸碱度
     * @oxygen 氧含量
     * @nitrogen 氨氮含量
     * @nitrite 亚硝酸盐含量
     */

    @Id(autoincrement = true)
    Long id;
    double temperature;
    double ph;
    double oxygen;
    double nitrogen;
    double nitrite;

    public OverallDataBean() {
    }

    @Generated(hash = 1495005162)
    public OverallDataBean(Long id, double temperature, double ph, double oxygen,
            double nitrogen, double nitrite) {
        this.id = id;
        this.temperature = temperature;
        this.ph = ph;
        this.oxygen = oxygen;
        this.nitrogen = nitrogen;
        this.nitrite = nitrite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
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
