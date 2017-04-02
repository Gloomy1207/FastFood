package com.gloomy.fastfood.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.gloomy.fastfood.listener.OnBaseActivityListener;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
public abstract class BaseFragment extends Fragment {
    protected OnBaseActivityListener mOnBaseActivityListener;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnBaseActivityListener = (OnBaseActivityListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnBaseActivityListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnBaseActivityListener = (OnBaseActivityListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnBaseActivityListener");
        }
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack) {
        ((BaseContainerFragment) getParentFragment()).replaceFragment(fragment, isAddToBackStack);
    }

    public void hideKeyboard() {
        mOnBaseActivityListener.hideKeyboard();
    }
}
