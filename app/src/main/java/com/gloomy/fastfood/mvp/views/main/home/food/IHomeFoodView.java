package com.gloomy.fastfood.mvp.views.main.home.food;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Food;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
public interface IHomeFoodView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onItemFoodClick(Food food);

    void onRefreshDataComplete();

    void notifyDataSetChanged();
}
