package com.gloomy.fastfood.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gloomy.fastfood.listener.OnBaseActivityListener;
import com.gloomy.fastfood.widgets.CustomProgressDialog;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity implements OnBaseActivityListener {

    private CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    @Override
    public void showProgressDialog() {
        mCustomProgressDialog.show(getFragmentManager(), CustomProgressDialog.class.getSimpleName());
    }

    @Override
    public void dismissProgressDialog() {
        if (mCustomProgressDialog != null && mCustomProgressDialog.isAdded()) {
            mCustomProgressDialog.dismiss();
        }
    }
}
