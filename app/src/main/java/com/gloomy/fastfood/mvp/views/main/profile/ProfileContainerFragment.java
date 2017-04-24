package com.gloomy.fastfood.mvp.views.main.profile;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.BaseContainerFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.frame_container)
public class ProfileContainerFragment extends BaseContainerFragment {

    @AfterViews
    void afterViews() {
        if (AuthSession.isLogIn()) {
            replaceFragment(ProfileFragment_.builder().build(), false);
        }
    }
}
