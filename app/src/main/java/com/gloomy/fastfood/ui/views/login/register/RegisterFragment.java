package com.gloomy.fastfood.ui.views.login.register;

import android.widget.EditText;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.login.register.RegisterPresenter;

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
public class RegisterFragment extends BaseFragment implements IRegisterView {

    @ViewById(R.id.edtUsername)
    EditText mEdtUsername;

    @ViewById(R.id.edtPassword)
    EditText mEdtPassword;

    @Bean
    RegisterPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }

    @Override
    public void onShowProgressDialog() {

    }

    @Override
    public void onDismissProgressDialog() {

    }

    @Override
    public void onNoInternetConnection() {

    }

    @Click(R.id.btnRegister)
    void onRegisterClick() {

    }
}
