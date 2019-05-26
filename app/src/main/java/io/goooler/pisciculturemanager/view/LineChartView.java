package io.goooler.pisciculturemanager.view;

import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.model.Constants;

/**
 * 对 LineChart 的简单封装，统一绘制出图表的风格和交互
 */

public class LineChartView {
    private LineChart chart;
    private List<Entry> entries = new ArrayList<>();

    public LineChartView(View rootView, int resId) {
        chart = rootView.findViewById(resId);
    }

    public LineChartView(View rootView, int resId, List<Entry> entries) {
        chart = rootView.findViewById(resId);
        this.entries = entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public void setDescription(String tag) {
        Description description = chart.getDescription();
        description.setEnabled(true);
        description.setText(tag);
    }

    public void notifyDatasetChanged(List<Entry> entries) {

    }

    public void draw() {
        setChartAttr(new LineDataSet(entries, Constants.LABLE));
    }

    private void setAxisAttr() {
        XAxis xAxis = chart.getXAxis();

    }

    private void setChartAttr(LineDataSet dataSet) {
        chart.setData(new LineData(dataSet));
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        //最后的绘制方法
        chart.invalidate();
    }
}
