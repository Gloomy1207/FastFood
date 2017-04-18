package com.gloomy.fastfood.widgets.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.gloomy.fastfood.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import lombok.Setter;
import lombok.experimental.Accessors;

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

    @Setter
    @Accessors(prefix = "m")
    private String mMessageText;

    @Setter
    @Accessors(prefix = "m")
    private String mButtonText;

    @AfterViews
    void afterViews() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mTvMessage.setText(mMessageText);
        mBtnClose.setText(mButtonText);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (mIsAdded) {
            return;
        }
        mIsAdded = true;
        super.show(manager, tag);
    }

    public void showWithCallback(FragmentManager manager, String tag, OnCustomMessageDialogListener listener) {
        show(manager, tag);
        mOnCustomMessageDialogListener = listener;
    }

    @Override
    public void dismiss() {
        mIsAdded = false;
        super.dismiss();
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
