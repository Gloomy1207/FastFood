package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@Getter
public class SearchPeopleResponse {
    @SerializedName("content")
    private List<User> users;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
