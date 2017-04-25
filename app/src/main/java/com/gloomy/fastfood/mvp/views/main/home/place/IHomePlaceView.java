package com.gloomy.fastfood.mvp.views.main.home.place;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.City;
import com.gloomy.fastfood.mvp.models.Province;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 12/04/2017.
 */
public interface IHomePlaceView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onRefreshComplete();

    void onItemHomeCityClick(City city);

    void onItemProvinceClick(Province province);

    void notifyDataSetChanged();
}
