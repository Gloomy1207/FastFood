package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.Topic;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/04/2017.
 */
@Getter
public class TopicResponse {
    @SerializedName("content")
    private List<Topic> topics;
    @SerializedName("last")
    private boolean isLast;
    @SerializedName("number")
    private int currentPage;
}
