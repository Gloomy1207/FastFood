package com.gloomy.fastfood.ui.views.detail.topic;

import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.detail.topic.TopicDetailPresenter;
import com.gloomy.fastfood.widgets.CustomFloatingButton;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
@EActivity(R.layout.activity_detail_topic)
public class TopicDetailActivity extends BaseActivity implements ITopicDetailView {

    @Bean
    TopicDetailPresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.textInputLayout)
    CustomTextInputLayout mCustomTextInputLayout;

    @ViewById(R.id.btnLike)
    CustomFloatingButton mBtnLike;

    @Extra
    int mTopicId;

    @Extra
    String mTopicName;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initHeaderBar(mHeaderBar, mTopicName);
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
        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }
}
