package com.gloomy.fastfood.mvp.presenters.detail.place.store;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.detail.place.store.IPlaceDetailStoreView;
import com.gloomy.fastfood.mvp.views.main.home.store.HomeStoreAdapter;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.widgets.SpacesItemDecoration;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@EBean
public class PlaceDetailStorePresenter extends BasePresenter implements HomeStoreAdapter.OnHomeStoreListener {

    @Setter
    @Accessors(prefix = "m")
    private IPlaceDetailStoreView mView;

    @Setter
    @Accessors(prefix = "m")
    private int mPlaceId;

    @Setter
    @Accessors(prefix = "m")
    private int mPlaceType;

    private static final int LAYOUT_COLUMN_NUM = 2;

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mRecyclerViewDecorationSpace;

    private List<Store> mStores = new ArrayList<>();
    private boolean mIsLastPage;
    private int mCurrentPage;

    public void getDataForStores() {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getPlaceStoreData(mPlaceId, mPlaceType, null, null, new Callback<HomeStoreResponse>() {
            @Override
            public void onResponse(Call<HomeStoreResponse> call, Response<HomeStoreResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                HomeStoreResponse storeResponse = response.body();
                mStores.addAll(storeResponse.getStores());
                mIsLastPage = storeResponse.isLast();
                mCurrentPage = storeResponse.getCurrentPage();
                if (!mStores.isEmpty()) {
                    mView.onLoadDataComplete();
                } else {
                    mView.onEmptyData(getString(R.string.empty_store_place_detail));
                }
            }

            @Override
            public void onFailure(Call<HomeStoreResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        HomeStoreAdapter adapter = new HomeStoreAdapter(mContext, mStores, this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(LAYOUT_COLUMN_NUM, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(mRecyclerViewDecorationSpace));
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    @Override
    public void onItemHomeStoreClick(int position) {
        mView.onItemHomeStoreClick(mStores.get(position));
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getPlaceStoreData(mPlaceId, mPlaceType, mCurrentPage, null, new Callback<HomeStoreResponse>() {
            @Override
            public void onResponse(Call<HomeStoreResponse> call, Response<HomeStoreResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                HomeStoreResponse storeResponse = response.body();
                mStores.addAll(storeResponse.getStores());
                mCurrentPage = storeResponse.getCurrentPage();
                mIsLastPage = storeResponse.isLast();
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<HomeStoreResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }
}
