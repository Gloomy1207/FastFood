package com.gloomy.fastfood.ui.presenters.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.login.ILoginActivityView;
import com.gloomy.fastfood.ui.views.login.LoginActivity;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 19/04/2017.
 */
@EBean
public class LoginActivityPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    ILoginActivityView mView;

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        LoginActivity activity = (LoginActivity) getContext();
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.frameLayoutContainer, fragment);
        transaction.commitAllowingStateLoss();
        activity.getFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        LoginActivity activity = (LoginActivity) getContext();
        boolean isPop = false;
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            isPop = true;
            activity.getFragmentManager().popBackStack();
        }
        return isPop;
    }
}
