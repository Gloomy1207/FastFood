package com.gloomy.fastfood.mvp.views.detail.store.menu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.detail.store.menu.StoreMenuPresenter;
import com.gloomy.fastfood.mvp.views.detail.food.FoodDetailActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EFragment(R.layout.fragment_store_menu)
public class StoreMenuFragment extends BaseFragment implements IStoreMenuView {

    @FragmentArg
    Store mStore;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @Bean
    StoreMenuPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.setStore(mStore);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.getStoreFoodData();
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
        mRecyclerView.setVisibility(View.VISIBLE);
        mTvEmptyMessage.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataEmpty(String message) {
        mRecyclerView.setVisibility(View.GONE);
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mTvEmptyMessage.setText(message);
    }

    @Override
    public void onFoodClick(Food food) {
        FoodDetailActivity_.intent(getActivity()).mFoodParcelable(Parcels.wrap(food)).start();
    }
}
