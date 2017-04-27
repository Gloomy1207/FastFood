package com.gloomy.fastfood.mvp.views.profile;

import android.app.Fragment;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.profile.ProfileActivityPresenter;
import com.gloomy.fastfood.mvp.views.main.profile.ProfileFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@EActivity(R.layout.frame_container)
public class ProfileActivity extends BaseActivity implements IProfileView {

    @Bean
    ProfileActivityPresenter mPresenter;

    @Extra
    User mUser;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.replaceFragment(ProfileFragment_.builder().mUser(mUser).build(), false);
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = mPresenter.popFragment();
        if (!isPopFragment) {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack) {
        mPresenter.replaceFragment(fragment, isAddToBackStack);
    }
}
