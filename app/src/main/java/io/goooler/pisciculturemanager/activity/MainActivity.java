package io.goooler.pisciculturemanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

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
import io.goooler.pisciculturemanager.service.RequestService;

public class MainActivity extends BaseActivity implements
        MainOverallFragment.OnFragmentInteractionListener,
        MainDetailFragment.OnFragmentInteractionListener,
        MainPersonFragment.OnFragmentInteractionListener,
        MainNotificationFragment.OnFragmentInteractionListener {
    private MainOverallFragment overallFragment;
    private MainDetailFragment detailFragment;
    private MainNotificationFragment notificationFragment;
    private MainPersonFragment personFragment;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MainFragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> fragmentList;
    private String[] tabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = find(R.id.main_viewpager);
        tabLayout = find(R.id.main_tablayout);

        initFragments();

        startService(new Intent(this, RequestService.class));
    }

    private void initFragments() {
        overallFragment = new MainOverallFragment();
        detailFragment = new MainDetailFragment();
        notificationFragment = new MainNotificationFragment();
        personFragment = new MainPersonFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(overallFragment);
        fragmentList.add(detailFragment);
        fragmentList.add(notificationFragment);
        fragmentList.add(personFragment);
        tabTitles = getResources().getStringArray(R.array.main_tab_titles);
        fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(fragmentPagerAdapter);
        //viewPager可以缓存的fragment页数，保障生命周期完整
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        //确保已登录的用户按返回键时直接退出应用
        if (BaseApplication.getUserInfoState().isSaved()) {
            ActivityCollector.finishAll();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
