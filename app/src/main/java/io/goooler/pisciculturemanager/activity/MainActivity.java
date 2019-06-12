package io.goooler.pisciculturemanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.adapter.MainFragmentPagerAdapter;
import io.goooler.pisciculturemanager.base.ActivityCollector;
import io.goooler.pisciculturemanager.base.BaseActivity;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.fragment.MainDetailFragment;
import io.goooler.pisciculturemanager.fragment.MainNotificationFragment;
import io.goooler.pisciculturemanager.fragment.MainOverallFragment;
import io.goooler.pisciculturemanager.fragment.MainPersonFragment;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.service.RequestService;
import io.goooler.pisciculturemanager.util.CalculateUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.ResUtil;
import io.goooler.pisciculturemanager.util.SpUtil;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends BaseActivity implements
        MainOverallFragment.OnFragmentInteractionListener,
        MainDetailFragment.OnFragmentInteractionListener,
        MainPersonFragment.OnFragmentInteractionListener,
        MainNotificationFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = find(R.id.main_viewpager);
        tabLayout = find(R.id.main_tablayout);

        initFragments();

        //初始化 MainActivity 之后 直接开启一个 service 处理网络请求等操作
        startService(new Intent(this, RequestService.class));
        EventBusUtil.register(this);
    }

    /**
     * 通过点击通知跳转到 singleTask 模式的 activity 传递参数在这里接收
     *
     * @param intent 通知里传递的 intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        gotoPage(intent.getIntExtra(Constants.GOTO_FRAGMENT_ID, Constants.NULL_FRAGMENT_ID));
    }

    /**
     * 初始化首页的几个 fragment 加入 viewPager
     */
    private void initFragments() {
        MainOverallFragment overallFragment = new MainOverallFragment();
        MainDetailFragment detailFragment = new MainDetailFragment();
        MainNotificationFragment notificationFragment = new MainNotificationFragment();
        MainPersonFragment personFragment = new MainPersonFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(overallFragment);
        fragmentList.add(detailFragment);
        fragmentList.add(notificationFragment);
        fragmentList.add(personFragment);
        String[] tabTitles = ResUtil.getStringArray(R.array.main_tab_titles);
        MainFragmentPagerAdapter fragmentPagerAdapter = new MainFragmentPagerAdapter(
                getSupportFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(fragmentPagerAdapter);
        //viewPager可以缓存的fragment页数，保障生命周期完整
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 在 onNewIntent 里接收跳转逻辑并下发，
     *
     * @param fragmentId 跳转页面的参数，-1为默认值不做任何处理
     */
    private void gotoPage(@Constants.FragmentId int fragmentId) {
        if (fragmentId > Constants.NULL_FRAGMENT_ID) {
            //首页四个 fragment 的区间
            if (CalculateUtil.isBetween(Constants.OVERALL_FRAGMENT_ID, Constants.PERSON_FRAGMENT_ID, fragmentId)) {
                //切换到对应的 tab
                tabLayout.getTabAt(fragmentId).select();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //确保已登录的用户按返回键时直接退出应用
        if (SpUtil.getUserInfoState().isSaved()) {
            ActivityCollector.finishAll();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        BaseApplication.destroyGlobalObject();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        if (eventType.isSameOne(EventType.OVERALL_TO_MAIN)) {
            gotoPage(Constants.DETAIL_FRAGMENT_ID);
        }
    }
}
