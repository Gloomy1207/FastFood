package com.gloomy.fastfood.auth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.utils.SharePreferencesUtils;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public final class AuthSession {
    private static final String KEY_AUTH_SESSION = "ApiSession#mAuthLogin";

    private static volatile AuthSession instance;

    private Context mApplicationContext;
    @Getter
    @Accessors(prefix = "m")
    private Auth mAuthLogin;

    private AuthSession(@NonNull Context context) {
        mApplicationContext = context;
        load();
    }

    @NonNull
    public static AuthSession getInstance() {
        if (instance == null) {
            synchronized (AuthSession.class) {
                instance = new AuthSession(ApiRequest.getApplicationContext());
            }
        }
        return instance;
    }

    public void setAuth(@Nullable Auth authLogin) {
        this.mAuthLogin = authLogin;
        saveAsJsonString(KEY_AUTH_SESSION, authLogin);
    }

    public void logout() {
        this.mAuthLogin = null;
        saveAsJsonString(KEY_AUTH_SESSION, null);
    }

    private void saveAsJsonString(@NonNull String key, @Nullable Object source) {
        if (source == null) {
            SharePreferencesUtils.remove(mApplicationContext, key);
            return;
        }
        Gson gson = new Gson();
        SharePreferencesUtils.saveString(mApplicationContext, key, gson.toJson(source));
    }

    private void load() {
        this.mAuthLogin = load(KEY_AUTH_SESSION, Auth.class);
    }

    private <T> T load(@NonNull String key, @NonNull Class<T> classOfT) {
        String json = SharePreferencesUtils.getString(mApplicationContext, key);
        if (json.length() != 0) {
            Gson gson = new Gson();
            return gson.fromJson(json, classOfT);
        }
        return null;
    }

    public static boolean isLogIn() {
        return getInstance().mAuthLogin != null;
    }
}

