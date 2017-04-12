package com.gloomy.fastfood.ui.presenters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by ChauHQ on 28/12/2016
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private int mPreviousTotal;
    private boolean mLoading = true;
    private int mThreshold;

    public abstract void onLoadMore();

    public EndlessScrollListener(int threshold) {
        mThreshold = threshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int firstVisibleItem = -1;
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                firstVisibleItem = firstVisibleItems[0];
            }
        } else {
            firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        int totalItemCount = layoutManager.getItemCount();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        if (!mLoading && firstVisibleItem != -1 && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + mThreshold)) {
            onLoadMore();
            mLoading = true;
        }

    }

    public void resetValue() {
        mPreviousTotal = 0;
        mLoading = true;
    }
}
