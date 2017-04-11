package com.gloomy.fastfood.ui.views.main.home.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.models.StoreImage;
import com.gloomy.fastfood.ui.views.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
public class HomeStoreAdapter extends BaseAdapter<HomeStoreAdapter.ItemHomeStoreVH> {

    private List<Store> mStores;
    private OnItemHomeStoreListener mOnItemHomeStoreListener;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

    public HomeStoreAdapter(@NonNull Context mContext, List<Store> stores, OnItemHomeStoreListener onItemHomeStoreListener) {
        super(mContext);
        mStores = stores;
        mOnItemHomeStoreListener = onItemHomeStoreListener;
    }

    @Override
    public ItemHomeStoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_store, parent, false);
        return new ItemHomeStoreVH(view, mOnItemHomeStoreListener);
    }

    @Override
    public void onBindViewHolder(ItemHomeStoreVH holder, int position) {
        Store store = mStores.get(position);
        List<StoreImage> storeImages = store.getStoreImages();
        if (storeImages != null && !storeImages.isEmpty()) {
            Picasso.with(getContext())
                    .load(R.drawable.dummy_img_demo)
                    .into(holder.mImgStore);
        }
        if (store.getStoreType() != null) {
            holder.mTvPlaceType.setText(store.getStoreType().getTypeName());
        }
        if (store.getStoreAddress() != null) {
            holder.mTvPlaceAddress.setText(store.getStoreAddress().getAddressName());
        }
        holder.mTvPlaceName.setText(store.getPlaceName());
        holder.mTvPlaceTime.setText(String.format("%s - %s", mSimpleDateFormat.format(store.getOpenTime()), mSimpleDateFormat.format(store.getCloseTime())));
        holder.mRatingBar.setRating(store.getAverageRating());
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    /**
     * ViewHolder for item HomeStore
     */
    static class ItemHomeStoreVH extends RecyclerView.ViewHolder {
        private final ImageView mImgStore;
        private final TextView mTvPlaceName;
        private final TextView mTvPlaceTime;
        private final TextView mTvPlaceType;
        private final AutofitTextView mTvPlaceAddress;
        private final RatingBar mRatingBar;

        ItemHomeStoreVH(View itemView, final OnItemHomeStoreListener onItemHomeStoreListener) {
            super(itemView);
            mImgStore = (ImageView) itemView.findViewById(R.id.imgStore);
            mTvPlaceAddress = (AutofitTextView) itemView.findViewById(R.id.tvPlaceAddress);
            mTvPlaceTime = (TextView) itemView.findViewById(R.id.tvPlaceTime);
            mTvPlaceName = (TextView) itemView.findViewById(R.id.tvPlaceName);
            mTvPlaceType = (TextView) itemView.findViewById(R.id.tvPlaceType);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemHomeStoreListener != null) {
                        onItemHomeStoreListener.onItemHomeStoreClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnItemHomeStoreListener interface
     */
    public interface OnItemHomeStoreListener {
        void onItemHomeStoreClick(int position);
    }
}
