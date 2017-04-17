package com.gloomy.fastfood.api;

import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.api.responses.RatingPeopleResponse;
import com.gloomy.fastfood.api.responses.RatingStoreResponse;
import com.gloomy.fastfood.api.responses.SearchFoodResponse;
import com.gloomy.fastfood.api.responses.SearchPeopleResponse;
import com.gloomy.fastfood.api.responses.SearchStoreResponse;
import com.gloomy.fastfood.api.responses.SearchTopicResponse;
import com.gloomy.fastfood.api.responses.TopicResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
interface ApiService {

    @GET("basic/place/home")
    Call<HomeStoreResponse> getHomeStoreData(@Query(ApiParameters.PAGE) Integer page,
                                             @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/food/home")
    Call<HomeFoodResponse> getHomeFoodData(@Query(ApiParameters.PAGE) Integer page,
                                           @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/location/home")
    Call<HomePlaceResponse> getHomePlaceData(@Query(ApiParameters.PAGE) Integer page,
                                             @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/place/search")
    Call<SearchStoreResponse> getSearchStoreData(@Query(ApiParameters.LAT) Double lat,
                                                 @Query(ApiParameters.LNG) Double lng,
                                                 @Query(ApiParameters.PAGE) Integer page,
                                                 @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/food/search")
    Call<SearchFoodResponse> getSearchFoodData(@Query(ApiParameters.LAT) Double lat,
                                               @Query(ApiParameters.LNG) Double lng,
                                               @Query(ApiParameters.PAGE) Integer page,
                                               @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/user/search")
    Call<SearchPeopleResponse> getSearchPeopleData(@Query(ApiParameters.PAGE) Integer page,
                                                   @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/topic/search")
    Call<SearchTopicResponse> getSearchTopicData(@Query(ApiParameters.PAGE) Integer page,
                                                 @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/place/rating")
    Call<RatingStoreResponse> getRatingStoreData(@Query(ApiParameters.PAGE) Integer page,
                                                 @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/user/rating")
    Call<RatingPeopleResponse> getRatingPeopleData(@Query(ApiParameters.PAGE) Integer page,
                                                   @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/topic/hot")
    Call<TopicResponse> getHotTopicData(@Query(ApiParameters.PAGE) Integer page,
                                        @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/topic/trending")
    Call<TopicResponse> getTrendingTopicData(@Query(ApiParameters.PAGE) Integer page,
                                             @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/topic/fresh")
    Call<TopicResponse> getFreshTopicData(@Query(ApiParameters.PAGE) Integer page,
                                          @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/topic/random")
    Call<TopicResponse> getRandomTopicData(@Query(ApiParameters.PAGE) Integer page,
                                           @Query(ApiParameters.SIZE) Integer size);
}
