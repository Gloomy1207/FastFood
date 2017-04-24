package com.gloomy.fastfood.mvp.views.main.home.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
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
    private OnHomeStoreListener mOnHomeStoreListener;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
    private final int mImageWidth;

    public HomeStoreAdapter(@NonNull Context context, List<Store> stores, OnHomeStoreListener onHomeStoreListener) {
        super(context);
        mStores = stores;
        mOnHomeStoreListener = onHomeStoreListener;
        mImageWidth = (int) ((ScreenUtil.getWidthScreen(context) - ScreenUtil.convertDpiToPixel(context, 20)) / 2);
    }

    @Override
    public ItemHomeStoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_store, parent, false);
        return new ItemHomeStoreVH(view, mOnHomeStoreListener);
    }

    @Override
    public void onBindViewHolder(ItemHomeStoreVH holder, int position) {
        Store store = mStores.get(position);
        Picasso.with(getContext())
                .load(store.getMainImage())
                .resize(mImageWidth, 0)
                .into(holder.mImgStore);
        if (store.getStoreAddress() != null) {
            holder.mTvPlaceAddress.setText(store.getStoreAddress().getAddressName());
        }
        holder.mTvPlaceName.setText(store.getStoreName());
        if (store.getOpenTime() != null && store.getCloseTime() != null) {
            holder.mTvPlaceTime.setText(String.format("%s - %s", mSimpleDateFormat.format(store.getOpenTime()), mSimpleDateFormat.format(store.getCloseTime())));
        }
        holder.mTvNumberStar.setText(String.valueOf(store.getAverageRating()));
        holder.mTvNumberRating.setText(String.valueOf(store.getNumberRating()));
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
        private final AutofitTextView mTvPlaceAddress;
        private final TextView mTvNumberStar;
        private final TextView mTvNumberRating;

        ItemHomeStoreVH(View itemView, final OnHomeStoreListener onHomeStoreListener) {
            super(itemView);
            mImgStore = (ImageView) itemView.findViewById(R.id.imgStore);
            mTvPlaceAddress = (AutofitTextView) itemView.findViewById(R.id.tvPlaceAddress);
            mTvPlaceTime = (TextView) itemView.findViewById(R.id.tvPlaceTime);
            mTvPlaceName = (TextView) itemView.findViewById(R.id.tvPlaceName);
            mTvNumberStar = (TextView) itemView.findViewById(R.id.tvNumberStar);
            mTvNumberRating = (TextView) itemView.findViewById(R.id.tvNumberRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onHomeStoreListener != null) {
                        onHomeStoreListener.onItemHomeStoreClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnHomeStoreListener interface
     */
    public interface OnHomeStoreListener {
        void onItemHomeStoreClick(int position);
    }
}
