package com.gloomy.fastfood.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.listener.OnBaseActivityListener;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog_;
import com.gloomy.fastfood.widgets.dialog.CustomProgressDialog;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity implements OnBaseActivityListener {

    private CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
    private CustomMessageDialog mCustomMessageDialog = CustomMessageDialog_.builder().build();

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

    @Override
    public void showMessageDialog() {
        mCustomMessageDialog.show(getFragmentManager(), CustomMessageDialog.class.getSimpleName());
    }

    @Override
    public void showMessageDialog(CustomMessageDialog.OnCustomMessageDialogListener listener) {
        mCustomMessageDialog.showWithCallback(getFragmentManager(), CustomMessageDialog.class.getSimpleName(), listener);
    }

    @Override
    public void dismissMessageDialog() {
        mCustomMessageDialog.dismiss();
    }

    @Override
    public void setMessageDialogText(String text) {
        mCustomMessageDialog.setMessage(text);
    }

    @Override
    public void setButtonMessageDialogText(String text) {
        mCustomMessageDialog.setButtonText(text);
    }

    @Override
    public void showNoInternetConnection() {
        mCustomMessageDialog.setMessage(getString(R.string.no_internet_connection));
        mCustomMessageDialog.setButtonText(getString(R.string.button_close));
        showMessageDialog();
    }
}
