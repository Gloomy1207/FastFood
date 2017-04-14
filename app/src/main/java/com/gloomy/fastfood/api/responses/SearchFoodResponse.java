package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.Food;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@Getter
public class SearchFoodResponse {
    @SerializedName("content")
    private List<Food> foods;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
