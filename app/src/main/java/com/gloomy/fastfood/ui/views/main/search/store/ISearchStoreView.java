package com.gloomy.fastfood.ui.views.main.search.store;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.ui.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 13/04/2017.
 */
public interface ISearchStoreView extends IBaseView {

    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onRefreshComplete();

    void notifyDataSetChanged();

    void onItemStoreClick(Store store);

    void requestPermission(String... permissions);
}
