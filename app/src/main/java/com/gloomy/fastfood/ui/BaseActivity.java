package com.gloomy.fastfood.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.listener.OnBaseActivityListener;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;
import com.gloomy.fastfood.widgets.CustomConfirmDialog_;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog_;
import com.gloomy.fastfood.widgets.dialog.CustomProgressDialog;
import com.gloomy.fastfood.widgets.dialog.RequestLoginDialog;
import com.gloomy.fastfood.widgets.dialog.RequestLoginDialog_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity implements OnBaseActivityListener {

    private CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
    private CustomMessageDialog mCustomMessageDialog = CustomMessageDialog_.builder().build();
    private RequestLoginDialog mRequestLoginDialog = RequestLoginDialog_.builder().build();
    private CustomConfirmDialog mConfirmDialog = CustomConfirmDialog_.builder().build();

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
    public void showMessageDialog(String message, String button) {
        mCustomMessageDialog.setMessageText(message);
        mCustomMessageDialog.setButtonText(button);
        mCustomMessageDialog.show(getFragmentManager(), CustomMessageDialog.class.getSimpleName());
    }

    @Override
    public void showMessageDialog(String message, String button, CustomMessageDialog.OnCustomMessageDialogListener listener) {
        mCustomMessageDialog.setMessageText(message);
        mCustomMessageDialog.setButtonText(button);
        mCustomMessageDialog.showWithCallback(getFragmentManager(), CustomMessageDialog.class.getSimpleName(), listener);
    }

    @Override
    public void dismissMessageDialog() {
        mCustomMessageDialog.dismiss();
    }

    @Override
    public void showNoInternetConnection() {
        showMessageDialog(getString(R.string.no_internet_connection), getString(R.string.button_close));
    }

    @Override
    public void showLoadDataFailure() {
        showMessageDialog(getString(R.string.load_data_error), getString(R.string.button_close));
    }

    @Override
    public void showLoginDialog() {
        mRequestLoginDialog.show(getFragmentManager(), RequestLoginDialog.class.getSimpleName());
    }

    @Override
    public void showConfirmDialog(String message, String buttonLeft, String buttonRight, CustomConfirmDialog.OnConfirmDialogListener listener) {
        mConfirmDialog.setContent(message);
        mConfirmDialog.setLeftButton(buttonLeft);
        mConfirmDialog.setRightButton(buttonRight);
        mConfirmDialog.setOnConfirmDialogListener(listener);
        mConfirmDialog.show(getFragmentManager(), CustomConfirmDialog.class.getSimpleName());
    }

    @Override
    public void dismissConfirmDialog() {
        mConfirmDialog.dismiss();
    }
}
