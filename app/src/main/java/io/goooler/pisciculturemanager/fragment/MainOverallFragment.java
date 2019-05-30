package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.OverallSingleBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.RequestUtil;
import io.goooler.pisciculturemanager.util.ResUtil;
import io.goooler.pisciculturemanager.util.ServiceRequestUtil;
import io.goooler.pisciculturemanager.util.ToastUtil;
import okhttp3.Response;

/**
 * 首页第一个 fragment
 */
public class MainOverallFragment extends BaseFragment implements
        OverallRecyclerViewAdapter.OnItemClickListener, OnRefreshListener, View.OnClickListener {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private OverallRecyclerViewAdapter recyclerViewAdapter;
    private Button modifyBtn;
    private Button cancelBtn;

    private List<OverallSingleBean> singleBeans = new ArrayList<>();
    private OverallDataBean dataBean;
    private String[] beanNames;
    //EditText 可编辑状态
    private boolean editable;
    private boolean editing;

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
        modifyBtn = find(rootView, R.id.modify);
        cancelBtn = find(rootView, R.id.cancel);
        dataBean = DatabaseUtil.getLatestOverallOne();
        //列表填充数据初始化
        beanNames = ResUtil.getStringArray(R.array.overall_data_single);
        fillDataToList();
        recyclerViewAdapter = new OverallRecyclerViewAdapter(singleBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        modifyBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
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
        //通知 MainActivity 切换 fragment
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_MAIN, null));
        //通知 MainDetailFragment 切换数据
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_DETAIL, new Integer(position)));
    }

    /**
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_SERVICE, null));
    }

    @Override
    public void onClick(View v) {
        if (v == modifyBtn) {
            if (!editable) {
                editable = true;
                modifyBtn.setText(R.string.commit);
                cancelBtn.setVisibility(View.VISIBLE);
            } else {
                commitChanges();
                restoreDefault();
            }
            recyclerViewAdapter.setEditable(editable);
        } else if (v == cancelBtn) {
            restoreDefault();
            recyclerViewAdapter.setEditable(editable);
        }
    }

    /**
     * 提交修改的表单数据
     */
    private void commitChanges() {
        dataBean = new OverallDataBean(System.currentTimeMillis() / 1000,
                getViewHolder(0).getValue(),
                getViewHolder(1).getValue(),
                getViewHolder(2).getValue(),
                getViewHolder(3).getValue(),
                getViewHolder(4).getValue());
        ServiceRequestUtil.postSync(dataBean, new RequestUtil.RequestListener() {
            @Override
            public void response(Response rawRseponse, String jsonString) {
                if (rawRseponse.isSuccessful()) {
                    EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_SERVICE, null));
                }
            }
        });
    }

    private OverallRecyclerViewAdapter.ViewHolder getViewHolder(int position) {
        return (OverallRecyclerViewAdapter.ViewHolder) recyclerView.getChildViewHolder(
                recyclerView.getLayoutManager().getChildAt(position));
    }

    //界面恢复默认状态
    private void restoreDefault() {
        modifyBtn.setText(R.string.modify);
        cancelBtn.setVisibility(View.INVISIBLE);
        editable = false;
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
                ToastUtil.showToast(R.string.data_refreshed);
            } else {
                ToastUtil.showToast(R.string.data_no_refreshed);
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
