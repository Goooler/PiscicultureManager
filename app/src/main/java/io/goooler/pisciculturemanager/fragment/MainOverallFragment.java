package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.adapter.OverallRecyclerViewAdapter;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.OverallSingleBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.ResUtil;

public class MainOverallFragment extends BaseFragment implements OverallRecyclerViewAdapter.OnItemClickListener, OnRefreshListener {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private OverallRecyclerViewAdapter recyclerViewAdapter;

    private List<OverallSingleBean> singleBeans = new ArrayList<>();
    private OverallDataBean dataBean;
    private String[] beanNames;
    private final int OXYGEN_POS = 0;
    private final int TEMPERATURE_POS = 1;
    private final int PH_POS = 2;
    private final int NITROGEN_POS = 3;
    private final int NITRITE_POS = 4;

    private OnFragmentInteractionListener mListener;

    public MainOverallFragment() {
        // Required empty public constructor
    }

    public static MainOverallFragment newInstance(String param1, String param2) {
        MainOverallFragment fragment = new MainOverallFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_overall, container, false);
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
    protected void initView(View rootView) {
        super.initView(rootView);
        recyclerView = find(rootView, R.id.overall_recycler);
        refreshLayout = find(rootView, R.id.overall_refresh);

        dataBean = DatabaseUtil.getLatestOne();
        //列表填充数据初始化
        beanNames = ResUtil.getStringArray(R.array.overall_data_single);
        fillDataToList();
        recyclerViewAdapter = new OverallRecyclerViewAdapter(singleBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
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
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case OXYGEN_POS:

                break;
            case TEMPERATURE_POS:

                break;
            case PH_POS:

                break;
            case NITROGEN_POS:

                break;
            case NITRITE_POS:

                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //下拉刷新触发请求
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_SERVICE, null));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        if (eventType.isSameOne(EventType.SERVICE_TO_OVERALL)) {
            if (eventType.isSuccessful()) {
                dataBean = (OverallDataBean) eventType.message;
                fillDataToList();
                recyclerViewAdapter.notifyDataSetChanged();
                BaseApplication.showToast(R.string.data_refreshed);
            } else {
                BaseApplication.showToast(R.string.data_no_refreshed);
            }
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 把 OverallDataBean 各个成员填充
     * TODO: 这里可以考虑使用 OverallDataBean 数据类型替代，有时间改过来
     */
    private void fillDataToList() {
        singleBeans.clear();
        singleBeans.add(new OverallSingleBean(beanNames[0], dataBean.getOxygen()));
        singleBeans.add(new OverallSingleBean(beanNames[1], dataBean.getTemperature()));
        singleBeans.add(new OverallSingleBean(beanNames[2], dataBean.getPh()));
        singleBeans.add(new OverallSingleBean(beanNames[3], dataBean.getNitrogen()));
        singleBeans.add(new OverallSingleBean(beanNames[4], dataBean.getNitrite()));
    }


}
