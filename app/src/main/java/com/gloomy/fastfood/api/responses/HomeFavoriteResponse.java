package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.Store;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
@Getter
public class HomeFavoriteResponse {
    private boolean status;
    private String message;
    private FavoritePlaces places;

    /**
     * FavoritePlaces response
     */
    @Getter
    public static class FavoritePlaces {
        @SerializedName("content")
        private List<Store> stores;
        @SerializedName("number")
        private int currentPage;
        @SerializedName("last")
        private boolean isLast;
    }
}
