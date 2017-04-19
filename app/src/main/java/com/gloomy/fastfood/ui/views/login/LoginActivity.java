package com.gloomy.fastfood.ui.views.login;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.login.LoginActivityPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@Fullscreen
@EActivity(R.layout.frame_container)
public class LoginActivity extends BaseActivity implements ILoginActivityView {

    @Bean
    LoginActivityPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.replaceFragment(LoginFragmentFragment_.builder().build(), false);
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = mPresenter.popFragment();
        if (!isPopFragment) {
            super.onBackPressed();
        }
    }
}
