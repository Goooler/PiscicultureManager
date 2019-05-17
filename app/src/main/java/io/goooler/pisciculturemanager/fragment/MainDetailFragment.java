package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.Constants;

public class MainDetailFragment extends BaseFragment {
    private LineChart chart;

    private List<Entry> entries = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public MainDetailFragment() {
    }

    public static MainDetailFragment newInstance(String param1, String param2) {
        MainDetailFragment fragment = new MainDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        chart = find(rootView, R.id.chart);
        createTestChart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_detail, container, false);
        initView(rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //生成一组数据绘制折线图，用于测试
    private void createTestChart() {
        try {
            JSONArray jsonArray = JSON.parseObject(readJsonFromAssets()).getJSONArray(Constants.COORDINATES);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                entries.add(new Entry(object.getFloat(Constants.X), object.getFloat(Constants.Y)));
            }
            LineDataSet dataSet = new LineDataSet(entries, Constants.LABLE);
            chart.setData(new LineData(dataSet));
            chart.invalidate();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //读取 assets 下的 LineChartTest.json
    private String readJsonFromAssets() {
        String jsonStrng = null;
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    BaseApplication.getAssetsManager().open(Constants.LINECHART_TEST_JSON)));
            while ((jsonStrng = reader.readLine()) != null) {
                builder.append(jsonStrng);
            }
            jsonStrng = builder.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStrng;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
