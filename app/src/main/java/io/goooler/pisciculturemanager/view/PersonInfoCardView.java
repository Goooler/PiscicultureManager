package io.goooler.pisciculturemanager.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.goooler.pisciculturemanager.R;

public class PersonInfoCardView extends CardView {
    public ImageView logoImg;
    public TextView titleTxt;

    public PersonInfoCardView(@NonNull Context context) {
        super(context);
    }

    public PersonInfoCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView(getContext());
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.card_person_page, this, true);
        logoImg = rootView.findViewById(R.id.card_img);
        titleTxt = rootView.findViewById(R.id.card_txt);
    }

    public void setContent(int imgId, int txtId) {
        logoImg.setImageResource(imgId);
        titleTxt.setText(txtId);
    }

    public void setContent(String txtName, String imgName) {
        logoImg.setImageResource(getResources().getIdentifier(imgName, "mipmap", getContext().getPackageName()));
        titleTxt.setText(txtName);
    }
}
