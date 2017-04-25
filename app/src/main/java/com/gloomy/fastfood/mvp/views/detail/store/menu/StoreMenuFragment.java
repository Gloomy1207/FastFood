package com.gloomy.fastfood.mvp.views.detail.store.menu;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Store;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EFragment(R.layout.fragment_store_menu)
public class StoreMenuFragment extends BaseFragment {

    @FragmentArg
    Store mStore;
}
