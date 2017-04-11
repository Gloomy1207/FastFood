package com.gloomy.fastfood.ui.presenters.main.home.store;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomeStoreResponse;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.models.StoreAddress;
import com.gloomy.fastfood.models.StoreImage;
import com.gloomy.fastfood.models.StoreType;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.home.store.HomeStoreAdapter;
import com.gloomy.fastfood.ui.views.main.home.store.IHomeStoreView;
import com.gloomy.fastfood.widgets.SpacesItemDecoration;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 10-Apr-17.
 */
@EBean
public class HomeStorePresenter extends BasePresenter implements Callback<HomeStoreResponse>, HomeStoreAdapter.OnItemHomeStoreListener {

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mRecyclerViewDecorationSpace;

    public static final int LAYOUT_COLUMN_NUM = 2;

    @Setter
    @Accessors(prefix = "m")
    private IHomeStoreView mView;

    private HomeStoreResponse mHomeStoreResponse;
    private List<Store> mStores;
    private boolean mIsLastPage;
    private int mCurrentPage;

    public void getDataForStores() {
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getHomeStoreData(null, null, this);
    }

    @Override
    public void onResponse(Call<HomeStoreResponse> call, Response<HomeStoreResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mHomeStoreResponse = response.body();
        mView.onLoadDataComplete();
    }

    @Override
    public void onFailure(Call<HomeStoreResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        /*mStores = mHomeStoreResponse.getStores();*/
        mStores = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStores.add(Store.builder()
                    .closeTime(new Time(System.currentTimeMillis()))
                    .openTime(new Time(System.currentTimeMillis()))
                    .description("This is description")
                    .placeName("This is place name")
                    .storeAddress(StoreAddress.builder().addressName("This is address name").build())
                    .storeImages(Collections.singletonList(StoreImage.builder().imagePath("https://i.ytimg.com/vi/b6dT4kyVUuY/maxresdefault.jpg").build()))
                    .storeType(StoreType.builder().typeName("Restaurant").build())
                    .averageRating(2.5f)
                    .build());
        }
        recyclerView.setHasFixedSize(true);
        HomeStoreAdapter adapter = new HomeStoreAdapter(mContext, mStores, this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(LAYOUT_COLUMN_NUM, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(mRecyclerViewDecorationSpace));
    }

    @Override
    public void onItemHomeStoreClick(int position) {
        mView.onItemHomeStoreClick(mStores.get(position));
    }
}
