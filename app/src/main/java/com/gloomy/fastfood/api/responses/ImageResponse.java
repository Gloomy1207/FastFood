package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.Image;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
@Getter
public class ImageResponse {
    @SerializedName("content")
    private List<Image> images;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
