package com.gloomy.fastfood.widgets;

import android.app.DialogFragment;
import android.app.FragmentManager;
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
 * Created by HungTQB on 23-Apr-17.
 */
@EFragment(R.layout.layout_confirm_dialog)
public class CustomConfirmDialog extends DialogFragment {

    private boolean mIsAdded;

    @ViewById(R.id.btnRight)
    TextView mBtnRight;

    @ViewById(R.id.btnLeft)
    TextView mBtnLeft;

    @ViewById(R.id.tvContent)
    TextView mTvContent;

    @Setter
    @Accessors(prefix = "m")
    private String mContent;

    @Setter
    @Accessors(prefix = "m")
    private String mLeftButton;

    @Setter
    @Accessors(prefix = "m")
    private String mRightButton;

    @Setter
    @Accessors(prefix = "m")
    private OnConfirmDialogListener mOnConfirmDialogListener;

    @AfterViews
    void afterViews() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mBtnLeft.setText(mLeftButton);
        mBtnRight.setText(mRightButton);
        mTvContent.setText(mContent);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (mIsAdded) {
            return;
        }
        mIsAdded = true;
        super.show(manager, tag);
    }

    @Override
    public void dismiss() {
        mIsAdded = false;
        super.dismiss();
    }

    @Click(R.id.btnLeft)
    void onLeftClick() {
        if (mOnConfirmDialogListener != null) {
            mOnConfirmDialogListener.onLeftClick();
        }
    }

    @Click(R.id.btnRight)
    void onRightClick() {
        if (mOnConfirmDialogListener != null) {
            mOnConfirmDialogListener.onRightClick();
        }
    }

    /**
     * OnConfirmDialogListener
     */
    public interface OnConfirmDialogListener {
        void onLeftClick();

        void onRightClick();
    }
}
