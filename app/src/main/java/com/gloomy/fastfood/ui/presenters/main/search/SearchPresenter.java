package com.gloomy.fastfood.ui.presenters.main.search;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.search.IViewSearch;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class SearchPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IViewSearch mView;
}
