package com.gloomy.fastfood.mvp.views.gallery;

import com.gloomy.fastfood.models.GalleryImage;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
public interface IGalleryView extends IBaseView {
    void onLoadMoreComplete();

    void onItemGalleryClick(GalleryImage galleryImage);

    void onLoadDataFailure();

    void onLoadDataComplete();

    void onBackButtonPressed();
}
