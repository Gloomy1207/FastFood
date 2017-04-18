package com.gloomy.fastfood.widgets.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.views.login.LoginActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@EFragment(R.layout.layout_request_login_dialog)
public class RequestLoginDialog extends DialogFragment {

    private boolean mIsAdded;

    @AfterViews
    void afterViews() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Click(R.id.btnJoin)
    void onJoinClick() {
        LoginActivity_.intent(getActivity())
                .start();
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        dismiss();
    }

    @Click(R.id.btnClose)
    void onCloseClick() {
        dismiss();
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
        super.dismiss();
        mIsAdded = false;
    }
}
