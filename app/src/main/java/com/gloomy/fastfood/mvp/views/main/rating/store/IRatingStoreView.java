package com.gloomy.fastfood.mvp.views.main.rating.store;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Store;

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
