package io.goooler.pisciculturemanager.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端接口 allData/%s 返回 json 对应的实体结构
 */
public class RequestDataBean {

    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "code")
    private int code;
    @JSONField(name = "time")
    private String time;
    @JSONField(name = "data")
    private List<OverallDataBean> beans = new ArrayList<>();

    public RequestDataBean() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<OverallDataBean> getBeans() {
        return beans;
    }

    public void setBeans(List<OverallDataBean> beans) {
        this.beans = beans;
    }
}
