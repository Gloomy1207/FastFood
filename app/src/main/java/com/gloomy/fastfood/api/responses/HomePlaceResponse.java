package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.City;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 12/04/2017.
 */
@Getter
public class HomePlaceResponse {
    @SerializedName("content")
    private List<City> cities;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
