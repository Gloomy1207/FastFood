package com.gloomy.fastfood.ui.views.main.home.place;

import com.gloomy.fastfood.models.City;
import com.gloomy.fastfood.models.Province;
import com.gloomy.fastfood.ui.IBaseView;

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
