package com.gloomy.fastfood.mvp.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
@Parcel
public class Province {
    int provinceId;
    String provinceName;
    City city;
    String numberPlaceText;

    @ParcelConstructor
    public Province(int provinceId, String provinceName, City city, String numberPlaceText) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.city = city;
        this.numberPlaceText = numberPlaceText;
    }
}
