package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.adapter.OverallRecyclerViewAdapter;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.OverallSingleBean;

public class MainOverallFragment extends BaseFragment implements OverallRecyclerViewAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private OverallRecyclerViewAdapter recyclerViewAdapter;

    private List<OverallSingleBean> singleBeans = new ArrayList<>();

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

        //列表填充数据初始化
        String[] beanNames = getContext().getResources().getStringArray(R.array.overall_data_single);
        for (int i = 0; i < beanNames.length; i++) {
            singleBeans.add(new OverallSingleBean(beanNames[i], 0));
        }
        recyclerViewAdapter = new OverallRecyclerViewAdapter(singleBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(this);
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
    public void onItemClick(View view, int position) {
        BaseApplication.showToast(position + "");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
