package com.gloomy.fastfood.ui.views.main;

import android.annotation.SuppressLint;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.main.MainPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IViewMain {

    private MainPresenter mPresenter = new MainPresenter();

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }
}
