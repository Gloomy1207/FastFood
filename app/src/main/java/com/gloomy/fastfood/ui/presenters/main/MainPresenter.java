package com.gloomy.fastfood.ui.presenters.main;

import com.gloomy.fastfood.ui.views.main.IViewMain;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
public class MainPresenter {

    @Setter
    @Accessors(prefix = "m")
    IViewMain mView;
}
