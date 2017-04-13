package com.gloomy.fastfood.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.api.responses.SearchStoreResponse;
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

    public void getHomeStoreData(String page, String size, Callback<HomeStoreResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHomeStoreData(page, size).enqueue(callback);
    }

    public void getHomeFoodData(String page, String size, Callback<HomeFoodResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHomeFoodData(page, size).enqueue(callback);
    }

    public void getHomePlaceData(String page, String size, Callback<HomePlaceResponse> callback) {
        ServiceHelper.createApiService(mApplicationContext).getHomePlaceData(page, size).enqueue(callback);
    }

    public void getSearchStoreData(String page, String size, LatLng latLng, Callback<SearchStoreResponse> callback) {
        if (latLng != null) {
            ServiceHelper.createApiService(mApplicationContext).getSearchStoreData(latLng.getLat(), latLng.getLng(), page, size).enqueue(callback);
        } else {
            ServiceHelper.createApiService(mApplicationContext).getSearchStoreData(null, null, page, size).enqueue(callback);
        }
    }
}
