package com.gloomy.fastfood.mvp.presenters.profile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.profile.IProfileView;
import com.gloomy.fastfood.mvp.views.profile.ProfileActivity;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@EBean
public class ProfileActivityPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IProfileView mView;

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        ProfileActivity activity = (ProfileActivity) getContext();
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.frameLayoutContainer, fragment);
        transaction.commitAllowingStateLoss();
        activity.getFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        ProfileActivity activity = (ProfileActivity) getContext();
        boolean isPop = false;
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            isPop = true;
            activity.getFragmentManager().popBackStack();
        }
        return isPop;
    }
}
