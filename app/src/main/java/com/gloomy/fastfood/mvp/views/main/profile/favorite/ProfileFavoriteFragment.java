package com.gloomy.fastfood.mvp.views.main.profile.favorite;

import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.observer.ProfileObserver;
import com.gloomy.fastfood.observer.listener.OnReceiveObserverListener;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.presenters.main.profile.favorite.ProfileFavoritePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 01-Apr-17.
 */
@EFragment(R.layout.fragment_profile_detail)
public class ProfileFavoriteFragment extends BaseFragment implements IProfileFavoriteView, OnReceiveObserverListener {

    @Bean
    ProfileFavoritePresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnectionMessage();
    }

    @Override
    public void onItemStoreClick(Store store) {
        // TODO: 18-Apr-17 Handle when click item store
    }

    @Override
    public void onLoadDataComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        ProfileObserver.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ProfileObserver.unregister(this);
    }

    @Override
    public void onReceiveProfileData(ProfileResponse profileResponse) {
        mPresenter.onReceiveProfileData(profileResponse);
    }
}
