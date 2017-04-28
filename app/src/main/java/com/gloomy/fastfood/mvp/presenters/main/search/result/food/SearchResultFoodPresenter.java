package com.gloomy.fastfood.mvp.presenters.main.search.result.food;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.home.food.HomeFoodAdapter;
import com.gloomy.fastfood.mvp.views.main.search.result.food.ISearchResultFoodView;
import com.gloomy.fastfood.widgets.SpacesItemDecoration;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
@EBean
public class SearchResultFoodPresenter extends BasePresenter implements HomeFoodAdapter.OnHomeFoodListener {
    private static final int NUM_COLUMN = 2;

    @Setter
    @Accessors(prefix = "m")
    ISearchResultFoodView mView;

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mDecorationSpace;

    private List<Food> mFoods = new ArrayList<>();


    public void onReceiveSearchResponse(SearchResultResponse response) {
        mFoods.clear();
        if (response.getFoods() != null && !response.getFoods().isEmpty()) {
            mFoods.addAll(response.getFoods());
            mView.onUpdateData();
        } else {
            mView.onEmptyData();
        }
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new HomeFoodAdapter(getContext(), mFoods, this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(mDecorationSpace));
    }

    @Override
    public void onFoodClick(int position) {
        mView.onFoodClick(mFoods.get(position));
    }
}
