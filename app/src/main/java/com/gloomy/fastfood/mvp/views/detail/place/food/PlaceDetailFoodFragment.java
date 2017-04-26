package com.gloomy.fastfood.mvp.views.detail.place.food;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.presenters.detail.place.food.PlaceDetailFoodPresenter;
import com.gloomy.fastfood.mvp.views.detail.food.FoodDetailActivity_;

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
@EFragment(R.layout.fragment_place_detail_food)
public class PlaceDetailFoodFragment extends BaseFragment implements IPlaceDetailFoodView {

    @FragmentArg
    int mPlaceId;

    @FragmentArg
    int mPlaceType;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @Bean
    PlaceDetailFoodPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.setPlaceId(mPlaceId);
        mPresenter.setPlaceType(mPlaceType);
        mPresenter.getPlaceFoodData();
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
    public void onLoadDataFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onLoadDataComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemFoodClick(Food food) {
        FoodDetailActivity_.intent(getActivity()).mFoodParcelable(Parcels.wrap(food)).start();
    }

    @Override
    public void onEmptyData(String message) {
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mTvEmptyMessage.setText(message);
    }
}
