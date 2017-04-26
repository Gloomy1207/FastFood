package com.gloomy.fastfood.api;

import com.gloomy.fastfood.api.requests.RatingStoreRequest;
import com.gloomy.fastfood.api.responses.CommentResponse;
import com.gloomy.fastfood.api.responses.DeleteCommentResponse;
import com.gloomy.fastfood.api.responses.HomeFavoriteResponse;
import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.api.responses.ImageResponse;
import com.gloomy.fastfood.api.responses.LikeResponse;
import com.gloomy.fastfood.api.responses.LoginResponse;
import com.gloomy.fastfood.api.responses.PlaceRatingResponse;
import com.gloomy.fastfood.api.responses.PostCommentResponse;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.api.responses.RatingPeopleResponse;
import com.gloomy.fastfood.api.responses.RatingStoreResponse;
import com.gloomy.fastfood.api.responses.RegistrationResponse;
import com.gloomy.fastfood.api.responses.SearchFoodResponse;
import com.gloomy.fastfood.api.responses.SearchPeopleResponse;
import com.gloomy.fastfood.api.responses.SearchStoreResponse;
import com.gloomy.fastfood.api.responses.SearchTopicResponse;
import com.gloomy.fastfood.api.responses.StoreFoodResponse;
import com.gloomy.fastfood.api.responses.TopicResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("auth/user/favorite")
    Call<HomeFavoriteResponse> getHomeFavoriteData(@Query(ApiParameters.PAGE) Integer page,
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

    @GET("auth/user/profile")
    Call<ProfileResponse> getProfile(@Query(ApiParameters.USERNAME) String username);

    @GET("auth/user/favorite")
    Call<ProfileResponse.ProfileFavorites> loadMoreUserFavorites(@Query(ApiParameters.PAGE) Integer page,
                                                                 @Query(ApiParameters.SIZE) Integer size);

    @GET("auth/user/feeds")
    Call<ProfileResponse.ProfileTopics> loadMoreUserFeed(@Query(ApiParameters.PAGE) Integer page,
                                                         @Query(ApiParameters.SIZE) Integer size);

    @POST("oauth/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field(ApiParameters.USERNAME) String username,
                              @Field(ApiParameters.PASSWORD) String password);

    @POST("oauth/facebook")
    @FormUrlEncoded
    Call<LoginResponse> loginWithFacebook(@Field(ApiParameters.FACEBOOK_TOKEN) String token,
                                          @Field(ApiParameters.FACEBOOK_ID) String id,
                                          @Field(ApiParameters.FULL_NAME) String name,
                                          @Field(ApiParameters.EMAIL) String email,
                                          @Field(ApiParameters.AVATAR) String avatar);

    @POST("oauth/register")
    @FormUrlEncoded
    Call<RegistrationResponse> register(@Field((ApiParameters.USERNAME)) String username,
                                        @Field((ApiParameters.PASSWORD)) String password,
                                        @Field(ApiParameters.EMAIL) String email);

    @GET("basic/image/food")
    Call<ImageResponse> getFoodImages(@Query(ApiParameters.FOOD_ID) int foodId,
                                      @Query(ApiParameters.PAGE) Integer page,
                                      @Query(ApiParameters.SIZE) Integer size);

    @GET("basic/topic/comment")
    Call<CommentResponse> getTopicComment(@Query(ApiParameters.TOPIC_ID) int topicId,
                                          @Query(ApiParameters.PAGE) Integer page,
                                          @Query(ApiParameters.SIZE) Integer size);

    @GET("auth/topic/like")
    Call<LikeResponse> likeTopic(@Query(ApiParameters.TOPIC_ID) int topicId);

    @POST("auth/topic/comment")
    Call<PostCommentResponse> commentTopic(@Query(ApiParameters.TOPIC_ID) int topicId,
                                           @Query(ApiParameters.CONTENT) String content);

    @GET("auth/topic/delete-comment")
    Call<DeleteCommentResponse> deleteTopicComment(@Query(ApiParameters.COMMENT_ID) int commentId);

    @GET("basic/image/topic")
    Call<ImageResponse> getTopicImage(@Query(ApiParameters.PAGE) Integer page,
                                      @Query(ApiParameters.SIZE) Integer size,
                                      @Query(ApiParameters.TOPIC_ID) int topicId);

    @GET("basic/place/comment")
    Call<CommentResponse> getStoreComment(@Query(ApiParameters.PLACE_ID) int placeId,
                                          @Query(ApiParameters.PAGE) Integer page,
                                          @Query(ApiParameters.SIZE) Integer size);

    @GET("auth/place/delete-comment")
    Call<DeleteCommentResponse> deleteStoreComment(@Query(ApiParameters.COMMENT_ID) int commentId);

    @GET("auth/place/like")
    Call<LikeResponse> favoriteStore(@Query(ApiParameters.PLACE_ID) int placeId);

    @POST("auth/place/comment")
    Call<PostCommentResponse> commentStore(@Query(ApiParameters.PLACE_ID) int topicId,
                                           @Query(ApiParameters.CONTENT) String content);

    @GET("basic/place/food")
    Call<StoreFoodResponse> getStoreFood(@Query(ApiParameters.PLACE_ID) int placeId,
                                         @Query(ApiParameters.PAGE) Integer page,
                                         @Query(ApiParameters.SIZE) Integer size);

    @POST("auth/place/rating")
    Call<PlaceRatingResponse> ratingPlace(@Body RatingStoreRequest request);
}
