package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.StoreFood;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 25-Apr-17.
 */
@Getter
public class StoreFoodResponse {
    private boolean status;
    private String message;
    private StoreFoodsPageable storeFoodsPageable;

    /**
     * StoreFoodPageable class
     */
    @Getter
    public static class StoreFoodsPageable {
        @SerializedName("content")
        private List<StoreFood> storeFoods;
        @SerializedName("number")
        private int currentPage;
        @SerializedName("last")
        private boolean isLast;
    }
}
