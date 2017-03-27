package com.gloomy.fastfood.ui.views.main.profile;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.profile.ProfilePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends BaseFragment implements IViewProfile {

    @Bean
    ProfilePresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }
}
