package com.gloomy.fastfood.mvp.views.detail.place.store;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Store;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
public interface IPlaceDetailStoreView extends IBaseView {
    void onLoadDataComplete();

    void onLoadDataFailure();

    void onItemHomeStoreClick(Store store);

    void onEmptyData(String message);
}
