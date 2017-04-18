package com.gloomy.fastfood.widgets.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.views.login.LoginActivity_;
import com.gloomy.fastfood.utils.ScreenUtil;

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
        }
    }

    @Click(R.id.btnJoin)
    void onJoinClick() {
        LoginActivity_.intent(getActivity())
                .start();
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
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

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = ScreenUtil.getWidthScreen(getActivity());
        if (window != null) {
            window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }
}
