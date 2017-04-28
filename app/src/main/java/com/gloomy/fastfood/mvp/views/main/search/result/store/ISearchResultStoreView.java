package com.gloomy.fastfood.mvp.views.main.search.result.store;

import com.gloomy.fastfood.mvp.models.Store;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public interface ISearchResultStoreView {
    void onStoreClick(Store store);

    void onUpdateData();

    void onEmptyData();
}
