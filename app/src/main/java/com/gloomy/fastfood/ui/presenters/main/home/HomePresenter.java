package com.gloomy.fastfood.ui.presenters.main.home;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.home.IViewHome;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class HomePresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IViewHome mView;
}
