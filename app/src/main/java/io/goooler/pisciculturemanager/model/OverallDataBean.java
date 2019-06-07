package io.goooler.pisciculturemanager.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.util.DateUtil;
import io.goooler.pisciculturemanager.util.ResUtil;

/**
 * 多个用途，同时是数据库的主表，关联 GreenDao
 */
@RequiresApi(api = Build.VERSION_CODES.O)
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

    public OverallDataBean(double oxygen, double temperature, double ph, double nitrogen, double nitrite) {
        this.timestamp = DateUtil.getCurrentTimeMillis();
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.ph = ph;
        this.nitrogen = nitrogen;
        this.nitrite = nitrite;
    }

    /**
     * 所有参数集合，关联 GreenDao 主表
     *
     * @param id          主键，自增长
     * @param timestamp   时间戳
     * @param oxygen      氧含量
     * @param temperature 温度
     * @param ph          酸碱度
     * @param nitrogen    氨氮含量
     * @param nitrite     亚硝酸盐含量
     */
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

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public String getShortDateString() {
        return DateUtil.timestampToShortDateString(timestamp);
    }

    /**
     * @return 各个参数对应的中文名的数组
     */
    public String[] getValueNames() {
        return ResUtil.getStringArray(R.array.overall_data_single);
    }

    /**
     * @return 各个参数 double 数组
     */
    public double[] getValues() {
        return new double[]{oxygen, temperature, ph, nitrogen, nitrite};
    }
}
