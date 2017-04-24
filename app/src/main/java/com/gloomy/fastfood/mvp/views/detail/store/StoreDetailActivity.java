package com.gloomy.fastfood.mvp.views.detail.store;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.presenters.detail.store.StoreDetailPresenter;
import com.gloomy.fastfood.widgets.CustomFloatingButton;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;

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

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.textInputLayout)
    CustomTextInputLayout mCommentLayout;

    @ViewById(R.id.btnLike)
    CustomFloatingButton mBtnLike;

    @ViewById(R.id.layoutParent)
    RelativeLayout mLayoutParent;

    @Extra
    Parcelable mStoreParcel;

    @AfterViews
    void afterViews() {
        mLayoutParent.requestFocus();
        Store store = Parcels.unwrap(mStoreParcel);
        mPresenter.setView(this);
        mPresenter.setStore(store);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.getTopicComment(false);
        mPresenter.initButtonLike(mBtnLike);
        mPresenter.initCommentLayout(mCommentLayout);
    }

    @Override
    public void onShowProgressDialog() {

    }

    @Override
    public void onDismissProgressDialog() {

    }

    @Override
    public void onNoInternetConnection() {

    }

    @Override
    public void onBackClick() {
        finish();
    }
}
