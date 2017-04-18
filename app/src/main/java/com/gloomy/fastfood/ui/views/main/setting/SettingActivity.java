package com.gloomy.fastfood.ui.views.main.setting;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.setting.SettingPresenter;
import com.gloomy.fastfood.ui.views.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@EActivity(R.layout.activity_setting)
public class SettingActivity extends BaseActivity implements ISettingView {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bean
    SettingPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
    }

    @Override
    public void onLogoutSuccess() {
        MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
    }
}
