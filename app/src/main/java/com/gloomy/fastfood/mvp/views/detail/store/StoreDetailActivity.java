package com.gloomy.fastfood.mvp.views.detail.store;

import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.detail.store.StoreDetailPresenter;
import com.gloomy.fastfood.widgets.CustomFloatingButton;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EActivity(R.layout.activity_detail_place)
public class StoreDetailActivity extends BaseActivity implements IStoreDetailView {

    @Bean
    StoreDetailPresenter mPresenter;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.textInputLayout)
    CustomTextInputLayout mCommentLayout;

    @ViewById(R.id.btnLike)
    FloatingActionButton mBtnLike;

    @ViewById(R.id.layoutParent)
    CoordinatorLayout mLayoutParent;

    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.imgPlace)
    ImageView mImgPlace;

    @ViewById(R.id.tvStoreName)
    TextView mTvStoreName;

    @ViewById(R.id.tvStoreTime)
    TextView mTvStoreTime;

    @ViewById(R.id.tvStoreAddress)
    TextView mTvStoreAddress;

    @Extra
    Parcelable mStoreParcel;

    @AfterViews
    void afterViews() {
        mLayoutParent.requestFocus();
        Store store = Parcels.unwrap(mStoreParcel);
        mPresenter.setView(this);
        mPresenter.setStore(store);
        mPresenter.setDataForViews();
        mPresenter.initViewPager(mViewPager, mTabLayout);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.initButtonLike(mBtnLike);
        mPresenter.initCommentLayout(mCommentLayout);
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
    public void onBackClick() {
        finish();
    }

    @Override
    public void onSetStoreName(String storeName) {
        mTvStoreName.setText(storeName);
    }

    @Override
    public void onSetStoreTime(String storeTime) {
        mTvStoreTime.setText(storeTime);
    }

    @Override
    public void onSetStoreAddress(String storeAddress) {
        mTvStoreAddress.setText(storeAddress);
    }

    @Override
    public void onSetStoreImage(String imagePath) {
        Picasso.with(this)
                .load(imagePath)
                .into(mImgPlace);
    }
}
