package com.gloomy.fastfood.mvp.views.detail.store.location;

import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
public interface IStoreDetailLocationView extends IBaseView {
    void onRequestPermission();

    void onCantGetLocation();

    void onDirectionFailure();

    void onBackClick();
}
