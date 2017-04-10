package com.gloomy.fastfood.ui.presenters.main.home.store;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.home.store.IHomeStoreView;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 10-Apr-17.
 */
@EBean
public class HomeStorePresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IHomeStoreView mView;
}
