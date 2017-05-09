package com.gloomy.fastfood.mvp.views.main.rating.store;

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
import com.gloomy.fastfood.mvp.models.Province;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.StoreAddress;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 16-Apr-17.
 */
public class RatingStoreAdapter extends BaseAdapter<RatingStoreAdapter.ItemRatingStoreVH> {

    private final List<Store> mStores;
    private final OnRatingStoreListener mOnRatingStoreListener;
    private final int mImageStoreSize;

    public RatingStoreAdapter(@NonNull Context mContext, List<Store> stores, OnRatingStoreListener onRatingStoreListener) {
        super(mContext);
        mStores = stores;
        mOnRatingStoreListener = onRatingStoreListener;
        mImageStoreSize = ScreenUtil.getWidthScreen(getContext());
    }

    @Override
    public ItemRatingStoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_rating_store, parent, false);
        return new ItemRatingStoreVH(view, mOnRatingStoreListener);
    }

    @Override
    public void onBindViewHolder(ItemRatingStoreVH holder, int position) {
        Store store = mStores.get(position);
        Picasso.with(getContext())
                .load(store.getMainImage())
                .resize(mImageStoreSize, 0)
                .into(holder.mImgStore);
        holder.mTvNumberStar.setText(String.valueOf(store.getAverageRating()));
        holder.mTvStoreName.setText(store.getStoreName());
        if (store.getStoreAddress() != null) {
            StoreAddress storeAddress = store.getStoreAddress();
            StringBuilder address = new StringBuilder(storeAddress.getAddressName());
            if (storeAddress.getProvince() != null) {
                Province province = storeAddress.getProvince();
                address.append(" - ").append(province.getProvinceName());
                if (province.getCity() != null) {
                    address.append(", ").append(province.getCity().getCityName());
                }
            }
            holder.mTvStoreAddress.setText(address.toString());
        }
        if (!TextUtils.isEmpty(store.getDescription())) {
            holder.mTvStoreDescription.setText(store.getDescription());
            holder.mTvStoreDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mTvStoreDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    /**
     * ViewHolder for item rating store
     */
    static class ItemRatingStoreVH extends RecyclerView.ViewHolder {
        private final ImageView mImgStore;
        private final TextView mTvNumberStar;
        private final TextView mTvStoreName;
        private final TextView mTvStoreAddress;
        private final TextView mTvStoreDescription;

        ItemRatingStoreVH(View itemView, final OnRatingStoreListener onRatingStoreListener) {
            super(itemView);
            mImgStore = (ImageView) itemView.findViewById(R.id.imgStore);
            mTvNumberStar = (TextView) itemView.findViewById(R.id.tvNumberStar);
            mTvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);
            mTvStoreAddress = (TextView) itemView.findViewById(R.id.tvStoreAddress);
            mTvStoreDescription = (TextView) itemView.findViewById(R.id.tvStoreDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRatingStoreListener != null) {
                        onRatingStoreListener.onItemStoreClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnRatingStoreListener interface
     */
    public interface OnRatingStoreListener {
        void onItemStoreClick(int position);
    }
}
