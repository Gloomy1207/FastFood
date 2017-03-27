package com.gloomy.fastfood.ui.views.main.search;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.search.SearchPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_search)
public class SearchFragment extends BaseFragment implements IViewSearch {

    @Bean
    SearchPresenter mPresenter;

    @AfterViews
    void afterViews() {

    }
}
