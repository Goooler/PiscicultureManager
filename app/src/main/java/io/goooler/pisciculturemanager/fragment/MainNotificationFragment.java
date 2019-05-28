package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.adapter.NotificationRecyclerViewAdapter;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.WarnningDataBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;

/**
 * 首页第三个 fragment
 */
public class MainNotificationFragment extends BaseFragment implements
        NotificationRecyclerViewAdapter.OnItemClickListener, OnLoadMoreListener {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NotificationRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Handler handler;

    private List<WarnningDataBean> warnningDataBeans;
    //默认显示最新10条数据
    private int queryNumber = 10;
    //记录列表的滑动位置
    private int lastPosition;

    private OnFragmentInteractionListener mListener;

    public MainNotificationFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_main_notification, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        recyclerView = find(rootView, R.id.notify_recycler);
        refreshLayout = find(rootView, R.id.notify_refresh);

        warnningDataBeans = DatabaseUtil.getLatestWarnning(queryNumber);
        recyclerViewAdapter = new NotificationRecyclerViewAdapter(warnningDataBeans);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(this);
        refreshLayout.setOnLoadMoreListener(this);
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

    @Override
    public void onItemClick(View view, int position) {

    }

    /**
     * 上拉加载更多的回调
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //记住最后滑动位置
        lastPosition = layoutManager.getPosition(layoutManager.getChildAt(0));
        //每次上拉加载更多10条
        queryNumber = queryNumber + 10;
        warnningDataBeans.clear();
        DatabaseUtil.getLatestWarnning(queryNumber, new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        warnningDataBeans.addAll((List<WarnningDataBean>) operation.getResult());
                        recyclerViewAdapter.notifyDataSetChanged();
                        //恢复最后滑动位置
                        layoutManager.scrollToPosition(lastPosition);
                        refreshLayout.finishLoadMore();
                    }
                });
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {

    }
}
