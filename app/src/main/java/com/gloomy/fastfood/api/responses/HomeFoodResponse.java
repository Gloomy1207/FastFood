package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.Food;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
@Getter
@Builder
public class HomeFoodResponse {
    @SerializedName("content")
    private List<Food> foods;
    private int totalPages;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
