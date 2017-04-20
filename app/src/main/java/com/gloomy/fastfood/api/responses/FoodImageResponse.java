package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.FoodImage;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
@Getter
public class FoodImageResponse {
    @SerializedName("content")
    private List<FoodImage> images;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
