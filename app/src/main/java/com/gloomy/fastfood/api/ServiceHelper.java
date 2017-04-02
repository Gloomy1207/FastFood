package com.gloomy.fastfood.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gloomy.fastfood.BuildConfig;
import com.gloomy.fastfood.R;
import com.gloomy.fastfood.auth.AuthSession;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public final class ServiceHelper {
    private static final String KEY_HEADER_AUTHORIZATION = "Authorization";
    private static final String API_TOKEN_PREFIX = "Bearer ";
    private static final int CONNECT_TIME_OUT = 120 * 1000;

    private ServiceHelper() {
        // No-op
    }

    static ApiService createApiService(@NonNull Context context) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS);
        httpClient.addInterceptor(interceptor);
        if (AuthSession.isLogIn()) {
            final String apiToken = AuthSession.getInstance().getAuthLogin().getApiToken();
            if (!TextUtils.isEmpty(apiToken)) {
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header(KEY_HEADER_AUTHORIZATION, API_TOKEN_PREFIX + apiToken)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                });
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_endpoint))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        return retrofit.create(ApiService.class);
    }
}
