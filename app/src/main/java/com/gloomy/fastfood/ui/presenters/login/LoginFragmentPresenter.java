package com.gloomy.fastfood.ui.presenters.login;

import android.text.TextUtils;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.LoginResponse;
import com.gloomy.fastfood.auth.Auth;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.login.ILoginView;
import com.gloomy.fastfood.utils.NetworkUtil;

import org.androidannotations.annotations.EBean;

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
    private ILoginView mView;

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
}
