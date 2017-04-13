package com.gloomy.fastfood.api;

import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.api.responses.SearchStoreResponse;

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

    @GET("basic/food/home")
    Call<HomeFoodResponse> getHomeFoodData(@Query(ApiParameters.PAGE) String page,
                                           @Query(ApiParameters.SIZE) String size);

    @GET("basic/location/home")
    Call<HomePlaceResponse> getHomePlaceData(@Query(ApiParameters.PAGE) String page,
                                             @Query(ApiParameters.SIZE) String size);

    @GET("basic/place/search")
    Call<SearchStoreResponse> getSearchStoreData(@Query(ApiParameters.LAT) Double lat,
                                                 @Query(ApiParameters.LNG) Double lng,
                                                 @Query(ApiParameters.PAGE) String page,
                                                 @Query(ApiParameters.SIZE) String size);
}
