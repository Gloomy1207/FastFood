package com.gloomy.fastfood.mvp.views.detail.place.province;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Province;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
public interface IPlaceDetailProvinceView extends IBaseView {
    void onProvinceClick(Province province);

    void onLoadDataComplete();

    void onLoadDataFailure();

    void onEmptyData(String message);
}
