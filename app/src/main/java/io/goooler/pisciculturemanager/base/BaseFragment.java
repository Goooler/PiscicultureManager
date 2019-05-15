package io.goooler.pisciculturemanager.base;

import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
    public final String NULL_STRING = "";
    public final String NULL_OBJECT = null;
    public final String SP_USERINFO = "user_config";

    public <T extends View> T find(View view, int resId) {
        return (T) view.findViewById(resId);
    }
}
