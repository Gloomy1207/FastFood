package com.gloomy.fastfood.ui.views.main.search;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseContainerFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.frame_container)
public class SearchContainerFragment extends BaseContainerFragment {

    @AfterViews
    void afterViews() {
        replaceFragment(SearchFragment_.builder().build(), false);
    }
}
