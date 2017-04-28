package com.gloomy.fastfood.mvp.views.main.search.result.food;

import com.gloomy.fastfood.mvp.models.Food;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public interface ISearchResultFoodView {
    void onUpdateData();

    void onFoodClick(Food food);

    void onEmptyData();
}
