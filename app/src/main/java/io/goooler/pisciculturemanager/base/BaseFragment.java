package io.goooler.pisciculturemanager.base;

import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public <T extends View> T find(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    protected void initView(View rootView) {
    }
}
