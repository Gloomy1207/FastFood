package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.Store;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
public class HomeStoreResponse {
    @SerializedName("content")
    private List<Store> stores;

    private int totalPages;

    @SerializedName("last")
    private boolean isLast;

    @SerializedName("number")
    private int currentPage;
}
