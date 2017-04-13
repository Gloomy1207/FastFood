package com.gloomy.fastfood.ui.presenters.main.home.food;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.main.home.food.HomeFoodAdapter;
import com.gloomy.fastfood.ui.views.main.home.food.IHomeFoodView;
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
 * Created by HungTQB on 11-Apr-17.
 */
@EBean
public class HomeFoodPresenter extends BasePresenter implements Callback<HomeFoodResponse>, HomeFoodAdapter.OnHomeFoodListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int RECYCLER_NUM_COLUMN = 2;
    private static final int LOAD_MORE_THRESHOLD = 15;

    @Setter
    @Accessors(prefix = "m")
    private IHomeFoodView mView;

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mDecorationSpace;


    private boolean mIsLastPage;
    private int mCurrentPage;
    private List<Food> mFoods = new ArrayList<>();
    private HomeFoodResponse mHomeFoodResponse;
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsRefresh;

    private View mDisableView;

    public void getHomeFoodData() {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            mView.onNoInternetConnection();
        }
        if (!mIsRefresh) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getHomeFoodData(null, null, this);
    }

    @Override
    public void onResponse(Call<HomeFoodResponse> call, Response<HomeFoodResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mHomeFoodResponse = response.body();
        mCurrentPage = mHomeFoodResponse.getCurrentPage();
        mIsLastPage = mHomeFoodResponse.isLast();
        if (!mIsRefresh) {
            mView.onLoadDataComplete();
        } else {
            mIsRefresh = false;
            mView.onRefreshDataComplete();
        }
    }

    @Override
    public void onFailure(Call<HomeFoodResponse> call, Throwable t) {
        mIsRefresh = false;
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        mFoods = mHomeFoodResponse.getFoods();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(RECYCLER_NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL);
        HomeFoodAdapter adapter = new HomeFoodAdapter(mContext, mFoods, this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(mDecorationSpace));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mEndlessScrollListener = new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        };
        recyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    @Override
    public void onFoodClick(int position) {
        mView.onItemFoodClick(mFoods.get(position));
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mDisableView = disableView;
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mDisableView.setVisibility(View.VISIBLE);
        getHomeFoodData();
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getHomeFoodData(String.valueOf(mCurrentPage), null, new Callback<HomeFoodResponse>() {
            @Override
            public void onResponse(Call<HomeFoodResponse> call, Response<HomeFoodResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                HomeFoodResponse foodResponse = response.body();
                mFoods.addAll(foodResponse.getFoods());
                mIsLastPage = foodResponse.isLast();
                mCurrentPage = foodResponse.getCurrentPage();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<HomeFoodResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    public void refreshData(RecyclerView recyclerView) {
        mCurrentPage = mHomeFoodResponse.getCurrentPage();
        mIsLastPage = mHomeFoodResponse.isLast();
        mFoods.clear();
        mFoods.addAll(mHomeFoodResponse.getFoods());
        if (recyclerView.getAdapter() == null) {
            initRecyclerView(recyclerView);
        } else {
            mView.notifyDataSetChanged();
        }
    }
}
