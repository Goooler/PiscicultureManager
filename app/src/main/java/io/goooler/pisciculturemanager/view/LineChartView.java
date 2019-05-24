package io.goooler.pisciculturemanager.view;

import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
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

    public void draw() {
        LineDataSet dataSet = new LineDataSet(entries, Constants.LABLE);
        chart.setData(new LineData(dataSet));
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        chart.invalidate();
    }
}
