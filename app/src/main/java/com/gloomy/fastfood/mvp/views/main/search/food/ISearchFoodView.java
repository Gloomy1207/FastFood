package com.gloomy.fastfood.mvp.views.main.search.food;

import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public interface ISearchFoodView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onRefreshComplete();

    void onItemSearchFoodClick(Food food);

    void notifyDataSetChanged();
}
