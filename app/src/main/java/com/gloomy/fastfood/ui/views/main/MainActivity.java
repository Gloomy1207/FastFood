package com.gloomy.fastfood.ui.views.main;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.main.MainPresenter;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IViewMain, BottomNavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.footerBar)
    BottomNavigationView mFooterBar;

    @Bean
    MainPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mFooterBar.setOnNavigationItemSelectedListener(this);
        mPresenter.initViewPager(mViewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mPresenter.onFooterBarItemSelect(item.getItemId());
        return true;
    }

    @Override
    public void onFooterBarItemClick(int position) {
        mViewPager.setCurrentItem(position, true);
    }
}
