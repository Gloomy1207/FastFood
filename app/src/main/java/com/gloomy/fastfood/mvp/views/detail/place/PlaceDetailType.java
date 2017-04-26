package com.gloomy.fastfood.mvp.views.detail.place;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({PlaceDetailType.CITY, PlaceDetailType.PROVINCE})
public @interface PlaceDetailType {
    int CITY = 1;
    int PROVINCE = 2;
}
