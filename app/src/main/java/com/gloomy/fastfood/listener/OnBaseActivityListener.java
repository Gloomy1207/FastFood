package com.gloomy.fastfood.listener;

import com.gloomy.fastfood.widgets.CustomConfirmDialog;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
public interface OnBaseActivityListener {
    void hideKeyboard();

    void showProgressDialog();

    void dismissProgressDialog();

    void showMessageDialog(String message, String button);

    void showMessageDialog(String message, String button, CustomMessageDialog.OnCustomMessageDialogListener listener);

    void dismissMessageDialog();

    void showNoInternetConnection();

    void showLoadDataFailure();

    void showLoginDialog();

    void showConfirmDialog(String message, String buttonLeft, String buttonRight, CustomConfirmDialog.OnConfirmDialogListener listener);

    void dismissConfirmDialog();
}
