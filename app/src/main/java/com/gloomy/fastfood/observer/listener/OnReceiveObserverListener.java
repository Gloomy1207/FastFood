package com.gloomy.fastfood.observer.listener;

import com.gloomy.fastfood.api.responses.ProfileResponse;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
public interface OnReceiveObserverListener {

    void onReceiveProfileData(ProfileResponse profileResponse);
}
