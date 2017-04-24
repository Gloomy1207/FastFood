package com.gloomy.fastfood.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
public class City {
    private int cityId;
    private String cityName;
    private double lat;
    private double lng;
    @SerializedName("first_five_provinces")
    private List<Province> provinces;
}
