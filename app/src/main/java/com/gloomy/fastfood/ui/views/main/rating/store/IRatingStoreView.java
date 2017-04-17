package com.gloomy.fastfood.ui.views.main.rating.store;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.ui.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 16-Apr-17.
 */
public interface IRatingStoreView extends IBaseView {

    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onItemStoreClick(Store store);
}
