package com.gloomy.fastfood.api.requests;

import com.gloomy.fastfood.api.ApiParameters;
import com.gloomy.fastfood.mvp.models.RatingType;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 26/04/2017.
 */
@Builder
@Getter

public class RatingStoreRequest {
    @SerializedName(ApiParameters.RATING_LIST)
    private List<RatingType> ratingTypes;
    @SerializedName(ApiParameters.PLACE_ID)
    private int placeId;
}
