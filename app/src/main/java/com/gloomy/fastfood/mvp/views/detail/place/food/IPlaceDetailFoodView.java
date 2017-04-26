package com.gloomy.fastfood.mvp.views.detail.place.food;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Food;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
public interface IPlaceDetailFoodView extends IBaseView {

    void onLoadDataFailure();

    void onLoadDataComplete();

    void onItemFoodClick(Food food);

    void onEmptyData(String message);
}
