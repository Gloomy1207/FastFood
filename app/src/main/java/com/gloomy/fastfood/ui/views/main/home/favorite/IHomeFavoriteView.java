package com.gloomy.fastfood.ui.views.main.home.favorite;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.ui.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
public interface IHomeFavoriteView extends IBaseView {
    void onStoreClick(Store store);

    void onLoadDataComplete();

    void onLoadDataFailure();

    void onLoadMoreComplete();

    void onEmptyData(String message);
}
