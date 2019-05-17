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

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.activity.LoginActivity;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.base.BaseFragment;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;
import io.goooler.pisciculturemanager.view.PersonInfoCardView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_person, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        avatarImg = find(rootView, R.id.avatar_img);
        usernameTxt = find(rootView, R.id.avatar_name);
        passwordCard = find(rootView, R.id.card_password);
        feedbackCard = find(rootView, R.id.card_feedback);
        infoCard = find(rootView, R.id.card_info);
        logoutCard = find(rootView, R.id.card_logout);

        String[] titles = getResources().getStringArray(R.array.main_person_card_title);
        String[] logos = getResources().getStringArray(R.array.main_person_card_logo);

        username = BaseApplication.getUserInfoState().getUsername();
        usernameTxt.setText(username);
        passwordCard.setContent(titles[0], logos[0]);
        feedbackCard.setContent(titles[1], logos[1]);
        infoCard.setContent(titles[2], logos[2]);
        logoutCard.setContent(titles[3], logos[3]);

        passwordCard.setOnClickListener(this);
        feedbackCard.setOnClickListener(this);
        infoCard.setOnClickListener(this);
        logoutCard.setOnClickListener(this);
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
    public void onClick(View v) {
        if (v == passwordCard) {

        } else if (v == feedbackCard) {

        } else if (v == infoCard) {

        } else if (v == logoutCard) {
            BaseApplication.setUserInfoState(new UserInfoStateBean(username, false));
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
