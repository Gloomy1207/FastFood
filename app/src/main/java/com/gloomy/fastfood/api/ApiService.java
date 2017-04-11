package com.gloomy.fastfood.api;

import com.gloomy.fastfood.api.responses.HomeStoreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public interface ApiService {

    @GET("basic/place/home")
    Call<HomeStoreResponse> getHomeStoreData(@Query(ApiParameters.PAGE) String page,
                                             @Query(ApiParameters.SIZE) String size);
}
