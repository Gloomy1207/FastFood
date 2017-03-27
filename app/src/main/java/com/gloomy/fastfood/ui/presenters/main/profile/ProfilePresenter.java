package com.gloomy.fastfood.ui.presenters.main.profile;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.profile.IViewProfile;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class ProfilePresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IViewProfile mView;
}
