package com.gloomy.fastfood.mvp.views.main.home.store;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 10-Apr-17.
 */
public interface IHomeStoreView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onRefreshComplete();

    void onItemHomeStoreClick(Store store);

    void notifyDataSetChanged();
}
