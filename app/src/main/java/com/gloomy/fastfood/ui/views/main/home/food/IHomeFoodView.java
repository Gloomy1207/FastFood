package com.gloomy.fastfood.ui.views.main.home.food;

import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
public interface IHomeFoodView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onItemFoodClick(Food food);
}
