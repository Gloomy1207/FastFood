package com.gloomy.fastfood.mvp.presenters.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.LoginResponse;
import com.gloomy.fastfood.auth.Auth;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.login.ILoginFragmentView;
import com.gloomy.fastfood.utils.NetworkUtil;

import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@EBean
public class LoginFragmentPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private ILoginFragmentView mView;

    private static final List<String> PERMISSION_FACEBOOK_NEEDS = Arrays.asList("email", "public_profile");

    public void signIn(String username, String password) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        if (!checkValidInformation(username, password)) {
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().login(username, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                LoginResponse loginResponse = response.body();
                if (!loginResponse.isStatus()) {
                    mView.onLoginFailure(loginResponse.getMessage());
                    return;
                }
                AuthSession.getInstance().setAuth(Auth.builder()
                        .apiToken(loginResponse.getAccessToken())
                        .build());
                mView.onLoginSuccessful();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onRequestFailure();
            }
        });
    }

    private boolean checkValidInformation(String username, String password) {
        boolean result = true;
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(username)) {
            result = false;
            builder.append(getString(R.string.username_is_required));
        }
        if (TextUtils.isEmpty(password)) {
            result = false;
            if (!TextUtils.isEmpty(builder.toString())) {
                builder.append("\n");
            }
            builder.append(getString(R.string.password_is_required));
        }
        if (!result) {
            mView.onLoginFailure(builder.toString());
        }
        return result;
    }

    public void initButtonLoginFacebook(LoginButton loginButton, CallbackManager callbackManager) {
        loginButton.setReadPermissions(PERMISSION_FACEBOOK_NEEDS);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getInformationFromFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // No-op
            }

            @Override
            public void onError(FacebookException error) {
                mView.onLoginFailure(error.getMessage());
            }
        });
    }

    private void getInformationFromFacebook(final AccessToken accessToken) {
        mView.onShowProgressDialog();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                mView.onDismissProgressDialog();
                try {
                    String id = object.getString("id");
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String avatar = String.format("https://graph.facebook.com/%s/picture?type=large", id);
                    loginWithFacebook(accessToken.getToken(), id, name, email, avatar);
                } catch (JSONException e) {
                    Log.d("TAG", "onCompleted: " + e.getMessage());
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void loginWithFacebook(String token, String id, String name, String email, String avatar) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().loginWithFacebook(token, id, name, email, avatar, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                LoginResponse loginResponse = response.body();
                AuthSession.getInstance().setAuth(Auth.builder().apiToken(loginResponse.getAccessToken()).build());
                mView.onLoginSuccessful();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onRequestFailure();
            }
        });
    }
}
