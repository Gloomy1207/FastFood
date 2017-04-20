package com.gloomy.fastfood.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.api.responses.LoginResponse;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.api.responses.RatingPeopleResponse;
import com.gloomy.fastfood.api.responses.RatingStoreResponse;
import com.gloomy.fastfood.api.responses.RegistrationResponse;
import com.gloomy.fastfood.api.responses.SearchFoodResponse;
import com.gloomy.fastfood.api.responses.SearchPeopleResponse;
import com.gloomy.fastfood.api.responses.SearchStoreResponse;
import com.gloomy.fastfood.api.responses.SearchTopicResponse;
import com.gloomy.fastfood.api.responses.TopicResponse;
import com.gloomy.fastfood.models.LatLng;

import retrofit2.Callback;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public final class ApiRequest {
    @SuppressLint("StaticFieldLeak")
    private static ApiRequest sInstance;
    private Context mApplicationContext;

    public static ApiRequest getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("Please call ApiRequest.initialize() first!");
        }
        return sInstance;
    }

    private ApiRequest() {
    }

    public static void initialize(@NonNull Context applicationContext) {
        if (sInstance == null) {
            sInstance = new ApiRequest();
        }
        sInstance.mApplicationContext = applicationContext;
    }

    public static Context getApplicationContext() {
        return sInstance.mApplicationContext;
    }

    public void getHomeStoreData(Integer page, Integer size, Callback<HomeStoreResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHomeStoreData(page, size).enqueue(callback);
    }

    public void getHomeFoodData(Integer page, Integer size, Callback<HomeFoodResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHomeFoodData(page, size).enqueue(callback);
    }

    public void getHomePlaceData(Integer page, Integer size, Callback<HomePlaceResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHomePlaceData(page, size).enqueue(callback);
    }

    public void getSearchStoreData(Integer page, Integer size, LatLng latLng, Callback<SearchStoreResponse> callback) {
        if (latLng != null) {
            ServiceHelper.createApiService(mApplicationContext).getSearchStoreData(latLng.getLat(), latLng.getLng(), page, size).enqueue(callback);
        } else {
            ServiceHelper.createApiService(mApplicationContext).getSearchStoreData(null, null, page, size).enqueue(callback);
        }
    }

    public void getSearchFoodData(Integer page, Integer size, LatLng latLng, Callback<SearchFoodResponse> callback) {
        if (latLng != null) {
            ServiceHelper.createApiService(mApplicationContext).getSearchFoodData(latLng.getLat(), latLng.getLng(), page, size).enqueue(callback);
        } else {
            ServiceHelper.createApiService(mApplicationContext).getSearchFoodData(null, null, page, size).enqueue(callback);
        }
    }

    public void getSearchPeopleData(Integer page, Integer size, Callback<SearchPeopleResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getSearchPeopleData(page, size).enqueue(callback);
    }

    public void getSearchTopicData(Integer page, Integer size, Callback<SearchTopicResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getSearchTopicData(page, size).enqueue(callback);
    }

    public void getRatingStoreData(Integer page, Integer size, Callback<RatingStoreResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getRatingStoreData(page, size).enqueue(callback);
    }

    public void getRatingPeopleData(Integer page, Integer size, Callback<RatingPeopleResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getRatingPeopleData(page, size).enqueue(callback);
    }

    public void getHotTopicData(Integer page, Integer size, Callback<TopicResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHotTopicData(page, size).enqueue(callback);
    }

    public void getTrendingTopicData(Integer page, Integer size, Callback<TopicResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getTrendingTopicData(page, size).enqueue(callback);
    }

    public void getFreshTopicData(Integer page, Integer size, Callback<TopicResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getFreshTopicData(page, size).enqueue(callback);
    }

    public void getRandomTopicData(Integer page, Integer size, Callback<TopicResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getRandomTopicData(page, size).enqueue(callback);
    }

    public void getProfile(String username, Callback<ProfileResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getProfile(username).enqueue(callback);
    }

    public void loadMoreUserFavorite(Integer page, Integer size, Callback<ProfileResponse.ProfileFavorites> callback) {
        ServiceHelper.createApiService(mApplicationContext).loadMoreUserFavorites(page, size).enqueue(callback);
    }

    public void loadMoreUserFeed(Integer page, Integer size, Callback<ProfileResponse.ProfileTopics> callback) {
        ServiceHelper.createApiService(mApplicationContext).loadMoreUserFeed(page, size).enqueue(callback);
    }

    public void login(String username, String password, Callback<LoginResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).login(username, password).enqueue(callback);
    }

    public void loginWithFacebook(String token, String id, String name, String email, String avatar, Callback<LoginResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).loginWithFacebook(token, id, name, email, avatar).enqueue(callback);
    }

    public void register(String username, String password, String email, Callback<RegistrationResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).register(username, password, email).enqueue(callback);
    }
}
