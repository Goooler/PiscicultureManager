package io.goooler.pisciculturemanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.Constants;

/**
 * 个人中心的几个卡片封装 view 复用
 */
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
        //通过指定类型和文件名去找资源 id
        logoImg.setImageResource(getResources().getIdentifier(imgName, Constants.MIPMAP, getContext().getPackageName()));
        titleTxt.setText(txtName);
    }
}
