package io.goooler.pisciculturemanager.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

import io.goooler.pisciculturemanager.R;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent, tvTime;
    private List<String> mXList;

    public CustomMarkerView(Context context, List<String> xData) {

        super(context, R.layout.custom_marker_view);
        tvContent = findViewById(R.id.tvContent);
        tvTime = findViewById(R.id.tv_marker_time);
        mXList = xData;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));

        } else {
            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
            String time = mXList.get((int) e.getX());
            tvTime.setText(time);
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
