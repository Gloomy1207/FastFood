package com.gloomy.fastfood.mvp.views.detail.store.menu;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Food;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 25-Apr-17.
 */
public interface IStoreMenuView extends IBaseView {
    void onLoadDataFailure();

    void onLoadDataComplete();

    void onLoadDataEmpty(String message);

    void onFoodClick(Food food);
}
