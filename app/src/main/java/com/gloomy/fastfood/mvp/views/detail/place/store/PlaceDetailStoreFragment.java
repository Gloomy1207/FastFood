package com.gloomy.fastfood.mvp.views.detail.place.store;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.detail.place.store.PlaceDetailStorePresenter;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@EFragment(R.layout.fragment_place_detail_store)
public class PlaceDetailStoreFragment extends BaseFragment implements IPlaceDetailStoreView {

    @FragmentArg
    int mPlaceId;

    @FragmentArg
    int mPlaceType;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @Bean
    PlaceDetailStorePresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.setPlaceType(mPlaceType);
        mPresenter.setPlaceId(mPlaceId);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.getDataForStores();
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
    public void onLoadDataComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onItemHomeStoreClick(Store store) {
        StoreDetailActivity_.intent(getActivity()).mStoreParcel(Parcels.wrap(store)).start();
    }

    @Override
    public void onEmptyData(String message) {
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mTvEmptyMessage.setText(message);
    }
}
