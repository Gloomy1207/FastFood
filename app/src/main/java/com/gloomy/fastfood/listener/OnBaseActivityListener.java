package com.gloomy.fastfood.listener;

import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
public interface OnBaseActivityListener {
    void hideKeyboard();

    void showProgressDialog();

    void dismissProgressDialog();

    void showMessageDialog();

    void showMessageDialog(CustomMessageDialog.OnCustomMessageDialogListener listener);

    void dismissMessageDialog();

    void setMessageDialogText(String text);

    void setButtonMessageDialogText(String text);
}
