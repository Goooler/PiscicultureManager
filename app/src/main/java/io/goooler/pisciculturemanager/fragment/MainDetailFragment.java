package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import io.goooler.pisciculturemanager.base.BaseApplication;
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

    public MainDetailFragment() {
    }

    public static MainDetailFragment newInstance(String param1, String param2) {
        MainDetailFragment fragment = new MainDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_detail, container, false);
        entries.add(new Entry(0, 0));
        chartView = rootView.findViewById(R.id.chart);
        chartView.setData(new LineData(new LineDataSet(entries, Constants.LABLE)));
        chartView.invalidate();
        return rootView;
    }

    /**
     * 这一页默认不加载，
     * TODO: 以后考虑可以更改逻辑
     */
    @Override
    public void loadData() {
    }

    private void loadChartDataSet(int paramId) {
        DatabaseUtil.getLatestOverall(Constants.LATEST_24, new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                entries.clear();
                List<OverallDataBean> beans = (List<OverallDataBean>) operation.getResult();
                for (int i = beans.size() - 1; i >= 0; i--) {
                    entries.add(new Entry(beans.get(i).getDateFloat(),
                            beans.get(i).getValueFloat(paramId)));
                }
                BaseApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        chartView.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
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
        EventBusUtil.unregister(this);
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        if (eventType.isSameOne(EventType.OVERALL_TO_DETAIL)) {
            loadChartDataSet((int) eventType.message);
        }
    }
}
