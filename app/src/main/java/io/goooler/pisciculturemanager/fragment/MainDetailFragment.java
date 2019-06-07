package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainDetailFragment extends BaseFragment {
    private LineChartView lineChart;

    private OnFragmentInteractionListener mListener;

    public MainDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_detail, container, false);
        lineChart = find(rootView, R.id.line_chart);
        return rootView;
    }

    /**
     * 这一页默认不加载，
     * TODO: 以后可以考虑更改逻辑
     */
    @Override
    public void loadData() {
    }

    /**
     * 查询最近24条数据并绘制折线图
     *
     * @param position 参数对应的 id，也是 OverallFragment 页面上参数对应的位置
     */
    private void loadDataAndRenderChart(@Constants.ItemPosition int position) {
        DatabaseUtil.getLatestOverall(Constants.LATEST_24, new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                List<OverallDataBean> beans = (List<OverallDataBean>) operation.getResult();
                if (beans.size() > 0) {
                    List<String> xData = new ArrayList<>();
                    List<String> yData = new ArrayList<>();
                    String label = beans.get(0).getValueNames()[position];
                    for (int i = beans.size() - 1; i >= 0; i--) {
                        xData.add(beans.get(i).getShortDateString());
                        yData.add(beans.get(i).getValues()[position] + Constants.NULL_STRING);
                    }
                    BaseApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            lineChart.setDataAndRender(xData, yData, label);
                        }
                    });
                }
            }
        });
    }

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
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        if (eventType.isSameOne(EventType.OVERALL_TO_DETAIL)) {
            loadDataAndRenderChart((int) eventType.message);
        }
    }
}
