package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.Store;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 13/04/2017.
 */
@Getter
public class SearchStoreResponse {
    @SerializedName("content")
    private List<Store> stores;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
