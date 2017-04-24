package com.gloomy.fastfood.mvp.views.login.register;

import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 19/04/2017.
 */
public interface IRegisterView extends IBaseView {
    void onShowInvalidMessage(String message);

    void onRequestFailure();

    void onRegistrationFailure(String message);

    void onRegisterSuccess(String message);
}
