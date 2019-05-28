package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.view.LineChartView;


/**
 * 首页第二个 fragment
 */
public class MainDetailFragment extends BaseFragment {
    private LineChartView chartView;

    private List<Entry> entries = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private Handler handler;

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
        entries.add(new Entry(0, 0));
        chartView = rootView.findViewById(R.id.chart);
        chartView.setData(new LineData(new LineDataSet(entries, Constants.LABLE)));
        chartView.invalidate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
        handler = new Handler();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
        handler.removeCallbacksAndMessages(null);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        if (eventType.isSameOne(EventType.OVERALL_TO_DETAIL)) {
            DatabaseUtil.getLatestOverall(Constants.LATEST_24, new AsyncOperationListener() {
                @Override
                public void onAsyncOperationCompleted(AsyncOperation operation) {
                    entries.clear();
                    List<OverallDataBean> beans = (List<OverallDataBean>) operation.getResult();
                    for (int i = beans.size() - 1; i >= 0; i--) {
                        entries.add(new Entry(beans.get(i).getDateFloat(),
                                beans.get(i).getValueFloat((Integer) eventType.message)));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            chartView.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }
}
