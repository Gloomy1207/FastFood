package com.gloomy.fastfood;

import android.content.res.Configuration;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.utils.LocaleUtil;

import java.util.Locale;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiRequest.initialize(this);
        setLanguage();
    }

    public void setLanguage() {
        Configuration configuration = getResources().getConfiguration();
        Locale locale = LocaleUtil.getInstance().getCurrentLocale(this);
        configuration.setLocale(locale);
        Locale.setDefault(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        createConfigurationContext(configuration);
    }
}
