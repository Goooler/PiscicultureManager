package io.goooler.pisciculturemanager.view;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;

public class LineChartView extends LineChart {

    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void invalidate() {
        initAttrs();
        super.invalidate();
    }

    /**
     * 各种属性参数在绘制之前设定，方便样式统一
     */
    private void initAttrs() {
        setDragEnabled(true);
        setScaleEnabled(true);
        setPinchZoom(true);
    }
}
