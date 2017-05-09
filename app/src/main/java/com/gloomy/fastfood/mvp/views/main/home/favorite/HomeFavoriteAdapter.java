package com.gloomy.fastfood.mvp.views.main.home.favorite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
public class HomeFavoriteAdapter extends BaseAdapter<HomeFavoriteAdapter.ItemHomeFavoriteVH> {
    private final List<Store> mStores;
    private final OnHomeFavoriteListener mOnHomeFavoriteListener;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
    private final int mStoreImageSize;

    public HomeFavoriteAdapter(@NonNull Context mContext, List<Store> stores, OnHomeFavoriteListener onHomeFavoriteListener) {
        super(mContext);
        mStores = stores;
        mOnHomeFavoriteListener = onHomeFavoriteListener;
        mStoreImageSize = ScreenUtil.getWidthScreen(getContext());
    }

    @Override
    public ItemHomeFavoriteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_favorite, parent, false);
        return new ItemHomeFavoriteVH(view, mOnHomeFavoriteListener);
    }

    @Override
    public void onBindViewHolder(ItemHomeFavoriteVH holder, int position) {
        Store store = mStores.get(position);
        Picasso.with(getContext())
                .load(store.getMainImage())
                .resize(mStoreImageSize, 0)
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
     * ViewHolder for item HomeFavorite
     */
    static class ItemHomeFavoriteVH extends RecyclerView.ViewHolder {
        private final ImageView mImgStore;
        private final TextView mTvNumberStar;
        private final TextView mTvNumberRating;
        private final TextView mTvPlaceName;
        private final TextView mTvPlaceTime;
        private final AutofitTextView mTvPlaceAddress;

        ItemHomeFavoriteVH(View itemView, final OnHomeFavoriteListener onHomeFavoriteListener) {
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
                    if (onHomeFavoriteListener != null) {
                        onHomeFavoriteListener.onStoreClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnHomeFavoriteListener interface
     */
    public interface OnHomeFavoriteListener {
        void onStoreClick(int position);
    }
}
