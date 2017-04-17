package com.gloomy.fastfood.ui.presenters.main.search.store;

import android.Manifest;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.SearchStoreResponse;
import com.gloomy.fastfood.models.LatLng;
import com.gloomy.fastfood.models.SearchStoreItem;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.main.search.store.ISearchStoreView;
import com.gloomy.fastfood.ui.views.main.search.store.SearchStoreAdapter;
import com.gloomy.fastfood.utils.LocationUtil;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.PermissionUtil;

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
 * Created by HungTQB on 13/04/2017.
 */
@EBean
public class SearchStorePresenter extends BasePresenter implements SearchStoreAdapter.OnSearchStoreListener, Callback<SearchStoreResponse>, SwipeRefreshLayout.OnRefreshListener {

    public static final int PERMISSION_REQUEST_CODE = 123;
    private static final int LOAD_MORE_THRESHOLD = 15;

    @Setter
    @Accessors(prefix = "m")
    private ISearchStoreView mView;

    private View mDisableView;
    private List<SearchStoreItem> mSearchStoreItems = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private SearchStoreResponse mSearchStoreResponse;
    private boolean mIsLastPage;
    private int mCurrentPage;
    private boolean mIsRefresh;
    private LatLng mCurrentLocation;

    public void initRecyclerView(RecyclerView recyclerView) {
        mSearchStoreItems.add(SearchStoreItem.TitleItem.builder().title(getString(R.string.nearly_store_text)).build());
        mSearchStoreItems = parseToSearchStoreItem(mSearchStoreResponse.getStores());
        SearchStoreAdapter adapter = new SearchStoreAdapter(mContext, mSearchStoreItems, this);
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
        ApiRequest.getInstance().getSearchStoreData(mCurrentPage, null, mCurrentLocation, new Callback<SearchStoreResponse>() {
            @Override
            public void onResponse(Call<SearchStoreResponse> call, Response<SearchStoreResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                SearchStoreResponse searchStoreResponse = response.body();
                mSearchStoreItems.addAll(parseToSearchStoreItem(searchStoreResponse.getStores()));
                mIsLastPage = searchStoreResponse.isLast();
                mCurrentPage = searchStoreResponse.getCurrentPage();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<SearchStoreResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mDisableView = disableView;
    }

    @Override
    public void onItemStoreClick(int position) {
        mView.onItemStoreClick(((SearchStoreItem.StoreItem) mSearchStoreItems.get(position)).getStore());
    }

    public void getDataSearchStore() {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            mView.onNoInternetConnection();
        }
        if (PermissionUtil.isPermissionsGranted(mContext, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (!mIsRefresh) {
                mView.onShowProgressDialog();
            }
            mCurrentLocation = LocationUtil.getCurrentLatLng(mContext);
            ApiRequest.getInstance().getSearchStoreData(null, null, mCurrentLocation, this);
        } else {
            mView.requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onResponse(Call<SearchStoreResponse> call, Response<SearchStoreResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mSearchStoreResponse = response.body();
        mCurrentPage = mSearchStoreResponse.getCurrentPage();
        mIsLastPage = mSearchStoreResponse.isLast();
        if (!mIsRefresh) {
            mView.onLoadDataComplete();
        } else {
            mView.onRefreshComplete();
        }
    }

    @Override
    public void onFailure(Call<SearchStoreResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
    }

    private List<SearchStoreItem> parseToSearchStoreItem(List<Store> stores) {
        List<SearchStoreItem> searchStoreItems = new ArrayList<>();
        for (Store store : stores) {
            searchStoreItems.add(SearchStoreItem.StoreItem.builder().store(store).build());
        }
        return searchStoreItems;
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults != null && permissions != null &&
                    grantResults.length == permissions.length) {
                getDataSearchStore();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        mIsRefresh = true;
        mDisableView.setVisibility(View.VISIBLE);
        getDataSearchStore();
    }

    public void refreshData(RecyclerView recyclerView) {
        mIsRefresh = false;
        mCurrentPage = mSearchStoreResponse.getCurrentPage();
        mIsLastPage = mSearchStoreResponse.isLast();
        mSearchStoreItems.clear();
        mSearchStoreItems.addAll(parseToSearchStoreItem(mSearchStoreResponse.getStores()));
        if (recyclerView.getAdapter() != null) {
            mView.notifyDataSetChanged();
        } else {
            initRecyclerView(recyclerView);
        }
    }
}
