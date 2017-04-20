package com.gloomy.fastfood.ui.views.login.register;

import android.content.Intent;
import android.widget.EditText;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.login.register.RegisterPresenter;
import com.gloomy.fastfood.ui.views.main.MainActivity_;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 19/04/2017.
 */
@EFragment(R.layout.fragment_register)
public class RegisterFragment extends BaseFragment implements IRegisterView, CustomMessageDialog.OnCustomMessageDialogListener {

    @ViewById(R.id.edtUsername)
    EditText mEdtUsername;

    @ViewById(R.id.edtPassword)
    EditText mEdtPassword;

    @ViewById(R.id.edtEmail)
    EditText mEdtEmail;

    @Bean
    RegisterPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnectionMessage();
    }

    @Click(R.id.btnRegister)
    void onRegisterClick() {
        mPresenter.register(mEdtUsername.getText().toString(), mEdtPassword.getText().toString(), mEdtEmail.getText().toString());
    }

    @Click(R.id.btnBack)
    void onBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onShowInvalidMessage(String message) {
        showMessageDialog(message, getString(R.string.button_close));
    }

    @Override
    public void onRequestFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onRegistrationFailure(String message) {
        showMessageDialog(message, getString(R.string.button_close));
    }

    @Override
    public void onRegisterSuccess(String message) {
        showMessageDialog(message, getString(R.string.button_close), this);
    }

    @Override
    public void onCloseClick() {
        MainActivity_.intent(getActivity())
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
    }
}
