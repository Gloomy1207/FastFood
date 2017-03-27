package com.gloomy.fastfood.ui.views.main.rating;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.rating.RatingPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_rating)
public class RatingFragment extends BaseFragment implements IViewRating {

    @Bean
    RatingPresenter mPresenter;

    @AfterViews
    void afterViews() {

    }
}
