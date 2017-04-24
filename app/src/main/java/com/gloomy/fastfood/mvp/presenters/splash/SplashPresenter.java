package com.gloomy.fastfood.mvp.presenters.splash;

import android.os.Handler;

import com.gloomy.fastfood.mvp.views.splash.IViewSplash;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
public class SplashPresenter {
    private static final int SPLASH_DELAY_TIME = 1000;

    @Setter
    @Accessors(prefix = "m")
    private IViewSplash mView;

    public void startSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.onSplashFinish();
            }
        }, SPLASH_DELAY_TIME);
    }
}
