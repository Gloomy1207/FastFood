package com.gloomy.fastfood.ui.presenters.login.register;

import android.text.TextUtils;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.RegistrationResponse;
import com.gloomy.fastfood.auth.Auth;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.login.register.IRegisterView;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.ValidationUtil;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 19/04/2017.
 */
@EBean
public class RegisterPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IRegisterView mView;

    public void register(String username, String password, String email) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        if (checkValid(username, password, email)) {
            mView.onShowProgressDialog();
            ApiRequest.getInstance().register(username, password, email, new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    mView.onDismissProgressDialog();
                    if (response == null || response.body() == null) {
                        return;
                    }
                    RegistrationResponse registrationResponse = response.body();
                    if (!registrationResponse.isStatus()) {
                        mView.onRegistrationFailure(registrationResponse.getMessage());
                    } else {
                        AuthSession.getInstance().setAuth(Auth.builder().apiToken(registrationResponse.getAccessToken()).build());
                        mView.onRegisterSuccess(registrationResponse.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    mView.onDismissProgressDialog();
                    mView.onRequestFailure();
                }
            });
        }
    }

    private boolean checkValid(String username, String password, String email) {
        boolean result = true;
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(username)) {
            builder.append(getString(R.string.username_is_required)).append('\n');
            result = false;
        }
        if (TextUtils.isEmpty(password)) {
            builder.append(getString(R.string.password_is_required)).append('\n');
            result = false;
        }
        if (TextUtils.isEmpty(email)) {
            builder.append(getString(R.string.email_is_required)).append('\n');
            result = false;
        } else if (!ValidationUtil.isEmail(email)) {
            builder.append(getString(R.string.not_an_email)).append("\n");
        }
        int lastIndexOfBreakLine = builder.lastIndexOf("\n");
        if (lastIndexOfBreakLine != -1) {
            builder.delete(lastIndexOfBreakLine, lastIndexOfBreakLine + 1);
        }
        if (!result) {
            mView.onShowInvalidMessage(builder.toString());
        }
        return result;
    }
}
