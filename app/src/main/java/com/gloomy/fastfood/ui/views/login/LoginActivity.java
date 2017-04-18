package com.gloomy.fastfood.ui.views.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@EActivity(R.layout.frame_container)
public class LoginActivity extends BaseActivity {

    @AfterViews
    void afterViews() {
        replaceFragment(LoginFragment_.builder().build(), false);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.frameLayoutContainer, fragment);
        transaction.commitAllowingStateLoss();
        getFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        boolean isPop = false;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            isPop = true;
            getFragmentManager().popBackStack();
        }
        return isPop;
    }
}
