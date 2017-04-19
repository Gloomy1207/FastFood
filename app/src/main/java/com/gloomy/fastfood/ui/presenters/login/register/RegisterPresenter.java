package com.gloomy.fastfood.ui.presenters.login.register;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.login.register.IRegisterView;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 19/04/2017.
 */
@EBean
public class RegisterPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IRegisterView mView;
}
