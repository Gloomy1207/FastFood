package com.gloomy.fastfood.ui.views.main.home.food;

import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.home.food.HomeFoodPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
@EFragment(R.layout.fragment_home_food)
public class HomeFoodFragment extends BaseFragment implements IHomeFoodView {

    @Bean
    HomeFoodPresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.getHomeFoodData();
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

    }

    @Override
    public void onLoadDataComplete() {
        mPresenter.initRecyclerView(mRecyclerView);
    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void onLoadDataFailure() {

    }

    @Override
    public void onItemFoodClick(Food food) {

    }
}
