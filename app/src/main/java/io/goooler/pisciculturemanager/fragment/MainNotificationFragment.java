package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.LogUtil;

public class MainNotificationFragment extends BaseFragment {
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public MainNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
        LogUtil.d("");
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
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {

    }
}
