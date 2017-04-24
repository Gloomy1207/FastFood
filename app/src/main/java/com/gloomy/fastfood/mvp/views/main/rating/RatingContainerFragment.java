package com.gloomy.fastfood.mvp.views.main.rating;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseContainerFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.frame_container)
public class RatingContainerFragment extends BaseContainerFragment {

    @AfterViews
    void afterViews() {
        replaceFragment(RatingFragment_.builder().build(), false);
    }
}
