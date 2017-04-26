package com.gloomy.fastfood.mvp.views.detail.place.province;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Province;
import com.gloomy.fastfood.mvp.presenters.detail.place.province.PlaceDetailProvincePresenter;
import com.gloomy.fastfood.mvp.views.detail.place.PlaceDetailActivity_;
import com.gloomy.fastfood.mvp.views.detail.place.PlaceDetailType;

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
@EFragment(R.layout.fragment_place_detail_province)
public class PlaceDetailProvinceFragment extends BaseFragment implements IPlaceDetailProvinceView {

    @FragmentArg
    int mCityId;

    @Bean
    PlaceDetailProvincePresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.setCityId(mCityId);
        mPresenter.getPlaceProvinceData();
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
    public void onProvinceClick(Province province) {
        PlaceDetailActivity_.intent(getActivity()).mPlaceParcelable(Parcels.wrap(province)).mPlaceType(PlaceDetailType.PROVINCE).start();
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
    public void onEmptyData(String message) {
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mTvEmptyMessage.setText(message);
    }
}
