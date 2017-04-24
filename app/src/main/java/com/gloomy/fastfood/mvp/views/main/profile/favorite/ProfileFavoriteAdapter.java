package com.gloomy.fastfood.mvp.views.main.profile.favorite;

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
import com.gloomy.fastfood.models.Province;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.models.StoreAddress;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.grantland.widget.AutofitTextView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
public class ProfileFavoriteAdapter extends BaseAdapter<ProfileFavoriteAdapter.ItemFavoriteVH> {
    private final List<Store> mStores;
    private final OnItemFavoriteListener mOnItemFavoriteListener;

    public ProfileFavoriteAdapter(@NonNull Context mContext, List<Store> stores, OnItemFavoriteListener onItemFavoriteListener) {
        super(mContext);
        mStores = stores;
        mOnItemFavoriteListener = onItemFavoriteListener;
    }

    @Override
    public ItemFavoriteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_profile_favorite, parent, false);
        return new ItemFavoriteVH(view, mOnItemFavoriteListener);
    }

    @Override
    public void onBindViewHolder(ItemFavoriteVH holder, int position) {
        Store store = mStores.get(position);
        Picasso.with(getContext())
                .load(store.getMainImage())
                .into(holder.mImgStore);
        holder.mTvNumberStar.setText(String.valueOf(store.getAverageRating()));
        holder.mTvNumberRating.setText(store.getNumberRating());
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
     * ViewHolder for item ProfileFavorites
     */
    static class ItemFavoriteVH extends RecyclerView.ViewHolder {
        private final ImageView mImgStore;
        private final TextView mTvNumberStar;
        private final TextView mTvNumberRating;
        private final TextView mTvStoreName;
        private final AutofitTextView mTvStoreAddress;
        private final TextView mTvStoreDescription;

        ItemFavoriteVH(View itemView, final OnItemFavoriteListener onItemFavoriteListener) {
            super(itemView);
            mImgStore = (ImageView) itemView.findViewById(R.id.imgStore);
            mTvNumberStar = (TextView) itemView.findViewById(R.id.tvNumberStar);
            mTvNumberRating = (TextView) itemView.findViewById(R.id.tvNumberRating);
            mTvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);
            mTvStoreAddress = (AutofitTextView) itemView.findViewById(R.id.tvStoreAddress);
            mTvStoreDescription = (TextView) itemView.findViewById(R.id.tvStoreDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemFavoriteListener != null) {
                        onItemFavoriteListener.onItemFavoriteClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnItemFavoriteListener interface
     */
    public interface OnItemFavoriteListener {
        void onItemFavoriteClick(int position);
    }
}
