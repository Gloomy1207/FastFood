package com.gloomy.fastfood.widgets.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.view.Window;
import android.widget.TextView;

import com.gloomy.fastfood.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 12-Apr-17.
 */
@EFragment(R.layout.layout_message_dialog)
public class CustomMessageDialog extends DialogFragment {

    @ViewById(R.id.tvMessage)
    TextView mTvMessage;

    @ViewById(R.id.btnClose)
    TextView mBtnClose;

    private boolean mIsAdded;
    private OnCustomMessageDialogListener mOnCustomMessageDialogListener;

    @AfterViews
    void afterViews() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    public void setMessage(String message) {
        mTvMessage.setText(message);
    }

    public void setButtonMessage(String buttonMessage) {
        mBtnClose.setText(buttonMessage);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (mIsAdded) {
            return;
        }
        super.show(manager, tag);
    }

    public void showWithCallback(FragmentManager manager, String tag, OnCustomMessageDialogListener listener) {
        show(manager, tag);
        mOnCustomMessageDialogListener = listener;
    }

    @Click(R.id.btnClose)
    void onCloseClick() {
        if (mOnCustomMessageDialogListener != null) {
            mOnCustomMessageDialogListener.onCloseClick();
            mOnCustomMessageDialogListener = null;
        }
        dismiss();
    }

    /**
     * OnCustomMessageDialogListener interface
     */
    public interface OnCustomMessageDialogListener {
        void onCloseClick();
    }
}
