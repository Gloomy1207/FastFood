package com.gloomy.fastfood.ui.presenters.main.home.place;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.models.City;
import com.gloomy.fastfood.models.Place;
import com.gloomy.fastfood.models.Province;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.main.home.place.HomePlaceAdapter;
import com.gloomy.fastfood.ui.views.main.home.place.IHomePlaceView;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 12/04/2017.
 */
@EBean
public class HomePlacePresenter extends BasePresenter implements Callback<HomePlaceResponse>, HomePlaceAdapter.OnHomePlaceListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int LOAD_MORE_THRESHOLD = 15;

    @Setter
    @Accessors(prefix = "m")
    private IHomePlaceView mView;

    private int mCurrentPage;
    private boolean mIsLastPage;
    private HomePlaceResponse mHomePlaceResponse;
    private List<Place> mPlaces = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsRefresh;

    private View mDisableView;

    public void getHomePlaceData() {
        if (mIsRefresh) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getHomePlaceData(null, null, this);
    }

    @Override
    public void onResponse(Call<HomePlaceResponse> call, Response<HomePlaceResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mHomePlaceResponse = response.body();
        mCurrentPage = mHomePlaceResponse.getCurrentPage();
        mIsLastPage = mHomePlaceResponse.isLast();
        if (!mIsRefresh) {
            mView.onLoadDataComplete();
        } else {
            mView.onRefreshComplete();
        }
    }

    @Override
    public void onFailure(Call<HomePlaceResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        mPlaces = parsePlaceData(mHomePlaceResponse.getCities());
        HomePlaceAdapter adapter = new HomePlaceAdapter(mContext, mPlaces, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        mEndlessScrollListener = new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        };
        recyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getHomePlaceData(String.valueOf(mCurrentPage), null, new Callback<HomePlaceResponse>() {
            @Override
            public void onResponse(Call<HomePlaceResponse> call, Response<HomePlaceResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                HomePlaceResponse placeResponse = response.body();
                mPlaces.addAll(parsePlaceData(placeResponse.getCities()));
                mIsLastPage = placeResponse.isLast();
                mCurrentPage = placeResponse.getCurrentPage();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<HomePlaceResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    private List<Place> parsePlaceData(List<City> cities) {
        List<Place> places = new ArrayList<>();
        for (City city : cities) {
            places.add(Place.PlaceCity.builder()
                    .city(city)
                    .build());
            boolean isFirstItem = true;
            for (Province province : city.getProvinces()) {
                places.add(Place.PlaceProvince.builder()
                        .province(province)
                        .isFirstItem(isFirstItem)
                        .build());
                isFirstItem = false;
            }
        }
        return places;
    }

    @Override
    public void onItemCityClick(int position) {
        mView.onItemHomeCityClick(((Place.PlaceCity) mPlaces.get(position)).getCity());
    }

    @Override
    public void onItemProvinceClick(int position) {
        mView.onItemProvinceClick(((Place.PlaceProvince) mPlaces.get(position)).getProvince());
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        mDisableView = disableView;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mDisableView.setVisibility(View.VISIBLE);
        mIsRefresh = true;
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        getHomePlaceData();
    }

    public void refreshData() {
        mIsRefresh = false;
        mCurrentPage = mHomePlaceResponse.getCurrentPage();
        mIsLastPage = mHomePlaceResponse.isLast();
        mPlaces.clear();
        mPlaces.addAll(parsePlaceData(mHomePlaceResponse.getCities()));
        mView.notifyDataSetChanged();
    }
}
