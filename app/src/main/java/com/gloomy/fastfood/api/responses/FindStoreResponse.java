package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.Store;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@Getter
public class FindStoreResponse {
    private boolean status;
    private String message;
    @SerializedName("place")
    private Store store;
}
