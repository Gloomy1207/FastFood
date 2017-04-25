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
public class StoreAddress {
    double lat;
    double lng;
    String addressName;
    Province province;

    @ParcelConstructor
    public StoreAddress(double lat, double lng, String addressName, Province province) {
        this.lat = lat;
        this.lng = lng;
        this.addressName = addressName;
        this.province = province;
    }
}
