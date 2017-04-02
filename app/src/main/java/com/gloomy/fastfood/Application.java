package com.gloomy.fastfood;

import com.gloomy.fastfood.api.ApiRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiRequest.initialize(this);
    }
}
