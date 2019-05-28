package io.goooler.pisciculturemanager.model;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.util.DateUtil;
import io.goooler.pisciculturemanager.util.ResUtil;

/**
 * 参数预警的几个指标，同时也是一张表
 */

@Entity
public class WarnningDataBean {

    @Id(autoincrement = true)
    private Long id;
    private long timestamp;
    private String paramName;
    private double value;
    private String unit;
    private String content;

    public WarnningDataBean() {
    }

    /**
     * 因参数过量发送通知的内容
     *
     * @param id        主键，自增长
     * @param timestamp 时间戳，关联主表中的时间戳
     * @param paramName 参数名
     * @param value     参数值
     * @param unit      参数的单位
     * @param content   保留字段，通知内容
     */
    public WarnningDataBean(Long id, long timestamp, String paramName, double value, String unit) {
        this.id = id;
        this.timestamp = timestamp;
        this.paramName = paramName;
        this.value = value;
        this.unit = unit;
    }

    @Generated(hash = 817707725)
    public WarnningDataBean(Long id, long timestamp, String paramName, double value,
                            String unit, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.paramName = paramName;
        this.value = value;
        this.unit = unit;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public String getDateString() {
        return DateUtil.timestampToDateString(timestamp);
    }

    public String getDescription() {
        return paramName + ResUtil.getString(R.string.param_overstandard) + +value + unit;
    }
}
