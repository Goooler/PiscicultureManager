package io.goooler.pisciculturemanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.activity.LoginActivity;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;
import io.goooler.pisciculturemanager.util.ResUtil;
import io.goooler.pisciculturemanager.util.SpUtil;
import io.goooler.pisciculturemanager.view.PersonInfoCardView;

/**
 * 首页第四个 fragment
 */
public class MainPersonFragment extends BaseFragment implements View.OnClickListener {
    private ImageView avatarImg;
    private TextView usernameTxt;
    private PersonInfoCardView passwordCard;
    private PersonInfoCardView feedbackCard;
    private PersonInfoCardView infoCard;
    private PersonInfoCardView logoutCard;

    private OnFragmentInteractionListener mListener;

    private String username;

    public MainPersonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_person, container, false);
        avatarImg = find(rootView, R.id.avatar_img);
        usernameTxt = find(rootView, R.id.avatar_name);
        passwordCard = find(rootView, R.id.card_password);
        feedbackCard = find(rootView, R.id.card_feedback);
        infoCard = find(rootView, R.id.card_info);
        logoutCard = find(rootView, R.id.card_logout);

        String[] titles = ResUtil.getStringArray(R.array.main_person_card_title);
        String[] logos = ResUtil.getStringArray(R.array.main_person_card_logo);

        username = SpUtil.getUserInfoState().getUsername();
        usernameTxt.setText(username);
        passwordCard.setContent(titles[0], logos[0]);
        feedbackCard.setContent(titles[1], logos[1]);
        infoCard.setContent(titles[2], logos[2]);
        logoutCard.setContent(titles[3], logos[3]);

        passwordCard.setOnClickListener(this);
        feedbackCard.setOnClickListener(this);
        infoCard.setOnClickListener(this);
        logoutCard.setOnClickListener(this);

        return rootView;
    }

    /**
     * 这一页默认不加载，
     * TODO: 以后考虑可以更改逻辑
     */
    @Override
    public void loadData() {

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
    public void onClick(View v) {
        if (v == passwordCard) {

        } else if (v == feedbackCard) {

        } else if (v == infoCard) {

        } else if (v == logoutCard) {
            SpUtil.setUserInfoState(new UserInfoStateBean(username, false));
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
