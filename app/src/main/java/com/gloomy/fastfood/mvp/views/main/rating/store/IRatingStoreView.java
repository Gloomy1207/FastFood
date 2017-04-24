package com.gloomy.fastfood.mvp.views.main.rating.store;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.IBaseView;

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
