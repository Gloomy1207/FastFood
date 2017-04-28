package com.gloomy.fastfood.mvp.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@Getter
public class QRCodeResult {
    @SerializedName("store_id")
    private int storeId;
}
