package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Getter
public class SearchResultResponse {
    @SerializedName("success")
    private boolean isSuccess;
    private List<Store> stores;
    private List<Food> foods;
    private List<User> peoples;
    private List<Topic> topics;
}
