package com.gloomy.fastfood.mvp.presenters.main.home;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.home.HomeViewPagerAdapter;
import com.gloomy.fastfood.mvp.views.main.home.IViewHome;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class HomePresenter extends BasePresenter {
    @StringArrayRes(R.array.fragment_home_view_pager_titles)
    String[] mTabTitles;

    private static final int[] TAB_ICONS = {
            R.drawable.ic_food_on,
            R.drawable.ic_store_on,
            R.drawable.ic_place_on,
            R.drawable.ic_favorite_on
    };

    @Setter
    @Accessors(prefix = "m")
    private IViewHome mView;

    private ViewPager mViewPager;
    private HomeViewPagerAdapter mAdapter;

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        mViewPager = viewPager;
        mAdapter = new HomeViewPagerAdapter(fragmentManager);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mTabTitles, TAB_ICONS, mContext);
        if (AuthSession.isLogIn()) {
            mViewPager.setOffscreenPageLimit(HomeViewPagerAdapter.PAGE_LOGIN_NUM);
        } else {
            mViewPager.setOffscreenPageLimit(HomeViewPagerAdapter.PAGE_NOT_LOGIN_NUM);
        }
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(getString(R.string.footer_bar_home));
        headerBar.setLeftButtonVisibility(View.INVISIBLE);
    }
}
