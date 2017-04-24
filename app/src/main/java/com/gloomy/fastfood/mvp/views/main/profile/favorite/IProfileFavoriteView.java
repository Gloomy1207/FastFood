package com.gloomy.fastfood.mvp.views.main.profile.favorite;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
public interface IProfileFavoriteView extends IBaseView {
    void onItemStoreClick(Store store);

    void onLoadDataComplete();

    void onLoadMoreComplete();
}
