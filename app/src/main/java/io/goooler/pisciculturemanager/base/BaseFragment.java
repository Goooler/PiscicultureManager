package io.goooler.pisciculturemanager.base;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    public <T extends View> T find(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    protected void initView(View rootView) {
    }
}
