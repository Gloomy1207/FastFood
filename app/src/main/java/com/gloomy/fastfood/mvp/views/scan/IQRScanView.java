package com.gloomy.fastfood.mvp.views.scan;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Store;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
public interface IQRScanView extends IBaseView {
    void onWrongTypeCode();

    void onFinishScan(Store store);

    void onNoStoreFound(String message);

    void onLoadDataFailure();

    void onOpenCamera();

    void onPermissionDenied();
}
