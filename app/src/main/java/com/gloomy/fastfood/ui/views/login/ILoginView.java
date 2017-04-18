package com.gloomy.fastfood.ui.views.login;

import com.gloomy.fastfood.ui.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
public interface ILoginView extends IBaseView {
    void onLoginFailure(String message);

    void onRequestFailure();

    void onLoginSuccessful();
}
