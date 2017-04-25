package com.gloomy.fastfood.mvp.views.main.search.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.SearchStoreItem;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.viewholders.ItemTitleVH;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 13/04/2017.
 */
public class SearchStoreAdapter extends BaseAdapter {

    private List<SearchStoreItem> mSearchStoreItems;
    private OnSearchStoreListener mOnSearchStoreListener;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

    @Override
    public int getItemViewType(int position) {
        return mSearchStoreItems.get(position).getType();
    }

    public SearchStoreAdapter(@NonNull Context mContext, List<SearchStoreItem> searchStoreItems, OnSearchStoreListener onSearchStoreListener) {
        super(mContext);
        mSearchStoreItems = searchStoreItems;
        mOnSearchStoreListener = onSearchStoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SearchStoreItem.SearchStoreItemType.STORE) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_search_store, parent, false);
            return new ItemSearchStoreVH(view, mOnSearchStoreListener);
        } else {
            return new ItemTitleVH(getContext(), parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemTitleVH) {
            onBindTitleItem((ItemTitleVH) holder, position);
        } else {
            onBindStoreItem((ItemSearchStoreVH) holder, position);
        }
    }

    private void onBindTitleItem(ItemTitleVH holder, int position) {
        holder.setTitle(((SearchStoreItem.TitleItem) mSearchStoreItems.get(position)).getTitle());
    }

    private void onBindStoreItem(ItemSearchStoreVH holder, int position) {
        Store store = ((SearchStoreItem.StoreItem) mSearchStoreItems.get(position)).getStore();
        Picasso.with(getContext())
                .load(store.getMainImage())
                .into(holder.mImgStore);
        holder.mTvPlaceName.setText(store.getStoreName());
        if (!TextUtils.isEmpty(store.getDescription())) {
            holder.mTvPlaceDescription.setText(store.getDescription());
            holder.mTvPlaceDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mTvPlaceDescription.setVisibility(View.GONE);
        }
        if (store.getOpenTime() != null && store.getCloseTime() != null) {
            holder.mTvPlaceTime.setText(String.format("%s - %s", mSimpleDateFormat.format(store.getOpenTime()), mSimpleDateFormat.format(store.getCloseTime())));
        }
    }

    @Override
    public int getItemCount() {
        return mSearchStoreItems.size();
    }

    /**
     * ViewHolder for item SearchStore
     */
    private static class ItemSearchStoreVH extends RecyclerView.ViewHolder {
        private final TextView mTvPlaceName;
        private final TextView mTvPlaceTime;
        private final TextView mTvPlaceDescription;
        private final ImageView mImgStore;
        private final TextView mTvViewMore;

        ItemSearchStoreVH(View itemView, final OnSearchStoreListener onSearchStoreListener) {
            super(itemView);
            mTvPlaceName = (TextView) itemView.findViewById(R.id.tvPlaceName);
            mTvPlaceTime = (TextView) itemView.findViewById(R.id.tvPlaceTime);
            mTvPlaceDescription = (TextView) itemView.findViewById(R.id.tvPlaceDescription);
            mImgStore = (ImageView) itemView.findViewById(R.id.imgStore);
            mTvViewMore = (TextView) itemView.findViewById(R.id.tvViewMore);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSearchStoreListener != null) {
                        onSearchStoreListener.onItemStoreClick(getLayoutPosition());
                    }
                }
            });
            mTvViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSearchStoreListener != null) {
                        onSearchStoreListener.onItemStoreClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnSearchStoreListener interface
     */
    public interface OnSearchStoreListener {
        void onItemStoreClick(int position);
    }
}
