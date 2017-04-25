package com.gloomy.fastfood.mvp.presenters.detail.store.menu;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.StoreFoodResponse;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.StoreFood;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.detail.store.menu.IStoreMenuView;
import com.gloomy.fastfood.mvp.views.detail.store.menu.StoreMenuAdapter;
import com.gloomy.fastfood.utils.NetworkUtil;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 25-Apr-17.
 */
@EBean
public class StoreMenuPresenter extends BasePresenter implements StoreMenuAdapter.OnStoreMenuListener {
    private static final int NUM_COLUMN = 2;

    @Setter
    @Accessors(prefix = "m")
    private IStoreMenuView mView;

    @Setter
    @Accessors(prefix = "m")
    private Store mStore;

    private List<StoreFood> mStoreFoods = new ArrayList<>();
    private boolean mIsLast;
    private int mCurrentPage;


    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new StoreMenuAdapter(getContext(), mStoreFoods, this));
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        if (mIsLast) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getStoreFood(mStore.getStoreId(), mCurrentPage, null, new Callback<StoreFoodResponse>() {
            @Override
            public void onResponse(Call<StoreFoodResponse> call, Response<StoreFoodResponse> response) {
                StoreFoodResponse foodResponse = response.body();
                if (foodResponse.isStatus()) {
                    StoreFoodResponse.StoreFoodsPageable storeFoodsPageable = foodResponse.getStoreFoodsPageable();
                    mStoreFoods.clear();
                    mStoreFoods.addAll(storeFoodsPageable.getStoreFoods());
                    mCurrentPage = storeFoodsPageable.getCurrentPage();
                    mIsLast = storeFoodsPageable.isLast();
                    mView.onLoadDataComplete();
                }
            }

            @Override
            public void onFailure(Call<StoreFoodResponse> call, Throwable t) {
                // No-op
            }
        });
    }

    public void getStoreFoodData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getStoreFood(mStore.getStoreId(), null, null, new Callback<StoreFoodResponse>() {
            @Override
            public void onResponse(Call<StoreFoodResponse> call, Response<StoreFoodResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                StoreFoodResponse foodResponse = response.body();
                if (foodResponse.isStatus()) {
                    StoreFoodResponse.StoreFoodsPageable storeFoodsPageable = foodResponse.getStoreFoodsPageable();
                    if (storeFoodsPageable != null) {
                        mStoreFoods.clear();
                        mStoreFoods.addAll(storeFoodsPageable.getStoreFoods());
                        mCurrentPage = storeFoodsPageable.getCurrentPage();
                        mIsLast = storeFoodsPageable.isLast();
                        mView.onLoadDataComplete();
                    }
                } else {
                    mView.onLoadDataEmpty(foodResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<StoreFoodResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onFoodClick(int position) {
        mView.onFoodClick(mStoreFoods.get(position).getFood());
    }
}
