package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
@Getter
public class ProfileResponse {
    private boolean status;
    private User user;
    private ProfileTopics topics;
    @SerializedName("favorite_places")
    private ProfileFavorites favorites;

    /**
     * ProfileTopics class
     */
    @Getter
    public static class ProfileTopics {
        @SerializedName("content")
        private List<Topic> topics;
        @SerializedName("number")
        private int currentPage;
        @SerializedName("last")
        private boolean isLast;
    }

    /**
     * ProfileFavorites class
     */
    @Getter
    public static class ProfileFavorites {
        @SerializedName("content")
        private List<Store> stores;
        @SerializedName("number")
        private int currentPage;
        @SerializedName("last")
        private boolean isLast;
    }
}
