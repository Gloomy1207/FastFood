package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.models.Topic;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 16-Apr-17.
 */
@Getter
public class SearchTopicResponse {
    @SerializedName("content")
    private List<Topic> topics;
    @SerializedName("last")
    private boolean isLast;
    @SerializedName("number")
    private int currentPage;
}
