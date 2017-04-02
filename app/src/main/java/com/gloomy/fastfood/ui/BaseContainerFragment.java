package com.gloomy.fastfood.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.gloomy.fastfood.R;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
public abstract class BaseContainerFragment extends Fragment {

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.frameLayoutContainer, fragment);
        transaction.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        boolean isPop = false;
        FragmentManager fragmentManager = getChildFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }
}
