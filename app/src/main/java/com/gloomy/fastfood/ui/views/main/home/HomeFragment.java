package com.gloomy.fastfood.ui.views.main.home;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.home.HomePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements IViewHome {

    @Bean
    HomePresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }
}
