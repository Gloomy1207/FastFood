package com.gloomy.fastfood.mvp.views.main.home.place;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.City;
import com.gloomy.fastfood.mvp.models.Province;
import com.gloomy.fastfood.mvp.presenters.main.home.place.HomePlacePresenter;
import com.gloomy.fastfood.mvp.views.detail.place.PlaceDetailActivity_;
import com.gloomy.fastfood.mvp.views.detail.place.PlaceDetailType;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
@EFragment(R.layout.fragment_home_place)
public class HomePlaceFragment extends BaseFragment implements IHomePlaceView {

    @Bean
    HomePlacePresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @ViewById(R.id.disableView)
    View mDisableView;

    @AfterViews
    void afterView() {
        mPresenter.setView(this);
        mPresenter.initSwipeRefresh(mSwipeRefreshLayout, mDisableView);
        mPresenter.getHomePlaceData();
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
        mPresenter.initRecyclerView(mRecyclerView);
    }

    @Override
    public void onLoadMoreComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        mDisableView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        showLoadDataFailure();
    }

    @Override
    public void onRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mPresenter.refreshData(mRecyclerView);
    }

    @Override
    public void onItemHomeCityClick(City city) {
        PlaceDetailActivity_.intent(getActivity()).mPlaceType(PlaceDetailType.CITY).mPlaceParcelable(Parcels.wrap(city)).start();
    }

    @Override
    public void onItemProvinceClick(Province province) {
        PlaceDetailActivity_.intent(getActivity()).mPlaceType(PlaceDetailType.PROVINCE).mPlaceParcelable(Parcels.wrap(province)).start();
    }

    @Override
    public void notifyDataSetChanged() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
