package io.goooler.pisciculturemanager.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.util.ResUtil;

/**
 * MPAndroidChart 中的 LineChart 简单封装，
 * 加入统一的属性设置，绘制方法
 */
@RequiresApi(api = Build.VERSION_CODES.O)
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
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAttrs();
    }

    /**
     * 初始化一些属性
     */
    private void initAttrs() {
        //设置整个图表的颜色
        setBackgroundResource(R.drawable.bg_line_chart);
        //描述文字，统一都是时间
        Description description = getDescription();
        description.setYOffset(10);
        description.setEnabled(true);
        description.setText(ResUtil.getString(R.string.param_time));
        //设置标签的位置(如 发电量 实时功率)
        Legend legend = getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //是否可以缩放、移动、触摸、缩放
        setTouchEnabled(true);
        setDragEnabled(true);
        setScaleEnabled(true);
        setPinchZoom(true);
        setDoubleTapToZoomEnabled(false);
        //获取左侧侧坐标轴、x 轴
        YAxis leftAxis = getAxisLeft();
        XAxis xAxis = getXAxis();
        //设置是否显示Y轴的值
        leftAxis.setDrawLabels(true);
        leftAxis.setTextColor(ResUtil.getColor(R.color.linechart));
        //设置所有垂直Y轴的的网格线是否显示
        leftAxis.setDrawGridLines(true);
        //设置虚线
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        //设置 Y 最值
        leftAxis.setAxisMinimum(0f);
        //将右边那条线隐藏
        getAxisRight().setEnabled(false);
        //设置X轴的位置，可上可下
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //将垂直于X轴的网格线隐藏，将X轴显示
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        //设置X轴上lable颜色和大小
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.GRAY);
        //设置X轴高度
        xAxis.setAxisLineWidth(1);
    }

    /**
     * 设置折线图横纵坐标数据并绘制
     *
     * @param xData 横坐标数据集
     * @param yData 纵坐标数据集
     * @param label 左上角显示的标签
     */
    public void setDataAndRender(List<String> xData, List<String> yData, String label) {
        //x 轴上显示标签处理
        getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                //对X轴上的值进行Format格式化，转成相应的值
                int intValue = (int) value;
                //筛选出自己需要的值，一般都是这样写没问题，并且一定要加上这个判断，不然会出错
                if (xData.size() > intValue && intValue >= 0) {
                    //这样显示在X轴上值就是 05:30  05:35，不然会是1.0  2.0
                    return xData.get(intValue);
                } else {
                    return Constants.NULL_STRING;
                }
            }
        });
        //设置 MarkerView
        CustomMarkerView mv = new CustomMarkerView(getContext(), xData);
        mv.setChartView(this);
        setMarker(mv);

        final ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < yData.size(); i++) {
            values.add(new Entry(i, Float.valueOf(yData.get(i)), xData.get(i)));
        }
        LineDataSet lineDataset;
        if (getData() != null && getData().getDataSetCount() > 0) {
            lineDataset = (LineDataSet) getData().getDataSetByIndex(0);
            lineDataset.setValues(values);
            lineDataset.setLabel(label);
            lineDataset.setDrawFilled(true);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else {
            lineDataset = new LineDataSet(values, label);
            lineDataset.setDrawFilled(true);
            lineDataset.setColor(ResUtil.getColor(R.color.linechart));
            //设置是否显示圆点
            lineDataset.setDrawCircles(false);
            //是否显示每个点的Y值
            lineDataset.setDrawValues(false);
            LineData lineData = new LineData(lineDataset);
            setData(lineData);
            animateX(1000);
        }
        invalidate();
    }
}
