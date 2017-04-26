package com.gloomy.fastfood.mvp.views.detail.place;

import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.presenters.detail.place.PlaceDetailPresenter;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@EActivity(R.layout.activity_detail_place)
public class PlaceDetailActivity extends BaseActivity implements IPlaceDetailView {
    @Bean
    PlaceDetailPresenter mPresenter;
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;
    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;
    @Extra
    Parcelable mPlaceParcelable;
    @Extra
    int mPlaceType;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.setPlace(mPlaceParcelable, mPlaceType);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.initViewPager(mViewPager, mTabLayout, getFragmentManager());
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnection();
    }

    @Override
    public void onBackPress() {
        finish();
    }
}
