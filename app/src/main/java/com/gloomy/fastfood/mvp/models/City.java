package com.gloomy.fastfood.mvp.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
@Parcel
public class City {
    int cityId;
    String cityName;
    double lat;
    double lng;
    @SerializedName("first_five_provinces")
    List<Province> provinces;

    @ParcelConstructor
    public City(int cityId, String cityName, double lat, double lng, List<Province> provinces) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.lat = lat;
        this.lng = lng;
        this.provinces = provinces;
    }
}
