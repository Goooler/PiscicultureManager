package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.OverallSingleBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.ToastUtil;

/**
 * 首页第一个 fragment
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainOverallFragment extends BaseFragment implements
        OverallRecyclerViewAdapter.OnItemClickListener, OnRefreshListener, View.OnClickListener {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private OverallRecyclerViewAdapter recyclerViewAdapter;
    private Button modifyBtn;
    private Button cancelBtn;

    private List<OverallSingleBean> singleBeans = new ArrayList<>();
    private OverallDataBean dataBean;
    //EditText 可编辑状态
    private boolean editable;

    private OnFragmentInteractionListener mListener;

    public MainOverallFragment() {
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_overall, container, false);
        recyclerView = find(rootView, R.id.overall_recycler);
        refreshLayout = find(rootView, R.id.overall_refresh);
        modifyBtn = find(rootView, R.id.modify);
        cancelBtn = find(rootView, R.id.cancel);
        recyclerViewAdapter = new OverallRecyclerViewAdapter(singleBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        modifyBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        recyclerViewAdapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void loadData() {
        dataBean = DatabaseUtil.getLatestOverallOne();
        fillDataToList(dataBean);
        recyclerViewAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(View view, @Constants.ItemPosition int position) {
        //通知 MainActivity 切换 fragment
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_MAIN, null));
        //通知 MainDetailFragment 切换数据
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_DETAIL, position));
    }

    /**
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_SERVICE_PULL, null));
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
        dataBean = new OverallDataBean(
                getViewHolder(0).getValue(),
                getViewHolder(1).getValue(),
                getViewHolder(2).getValue(),
                getViewHolder(3).getValue(),
                getViewHolder(4).getValue());
        //多一步要让列表上的数据更新为修改后的，因为后面在 setEditable 有 notifyDataSetChanged
        fillDataToList(dataBean);
        EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.OVERALL_TO_SERVICE_POST, dataBean));
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
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 把 overallDataBean 各个参数填充到 singleBeans
     */
    private void fillDataToList(OverallDataBean bean) {
        //列表填充数据初始化
        singleBeans.clear();
        for (int i = 0; i < bean.getValueNames().length; i++) {
            singleBeans.add(new OverallSingleBean(bean.getValueNames()[i], bean.getValues()[i]));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        switch (eventType.messageCode) {
            case EventType.SERVICE_TO_OVERALL_GET:
                if (eventType.isSuccessful()) {
                    //成功代表数据已刷新
                    dataBean = (OverallDataBean) eventType.message;
                    fillDataToList(dataBean);
                    recyclerViewAdapter.notifyDataSetChanged();
                    ToastUtil.showToast(R.string.data_refreshed);
                } else {
                    //失败代表暂无更新
                    ToastUtil.showToast(R.string.data_no_refreshed);
                }
                refreshLayout.finishRefresh();
                break;
            case EventType.SERVICE_TO_OVERALL_POST:
                if (eventType.isSuccessful()) {
                    ToastUtil.showToast(R.string.data_updated);
                }
                break;
            default:
                break;
        }
    }
}
