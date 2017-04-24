package com.gloomy.fastfood.mvp.views.main.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.presenters.main.home.HomePresenter;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements IViewHome {

    @Bean
    HomePresenter mPresenter;

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initViewPager(mViewPager, mTabLayout, getChildFragmentManager());
        mPresenter.initHeaderBar(mHeaderBar);
    }
}
