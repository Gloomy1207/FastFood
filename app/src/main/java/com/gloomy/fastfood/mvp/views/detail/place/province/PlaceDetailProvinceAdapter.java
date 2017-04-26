package com.gloomy.fastfood.mvp.views.detail.place.province;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Province;
import com.gloomy.fastfood.mvp.views.BaseAdapter;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
public class PlaceDetailProvinceAdapter extends BaseAdapter<PlaceDetailProvinceAdapter.ItemProvinceVH> {
    private final List<Province> mProvinces;
    private final OnPlaceDetailProvinceListener mOnPlaceDetailProvinceListener;

    public PlaceDetailProvinceAdapter(@NonNull Context mContext, List<Province> provinces, OnPlaceDetailProvinceListener onPlaceDetailProvinceListener) {
        super(mContext);
        mProvinces = provinces;
        mOnPlaceDetailProvinceListener = onPlaceDetailProvinceListener;
    }

    @Override
    public ItemProvinceVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_place_province, parent, false);
        return new ItemProvinceVH(view, mOnPlaceDetailProvinceListener);
    }

    @Override
    public void onBindViewHolder(ItemProvinceVH holder, int position) {
        Province province = mProvinces.get(position);
        holder.mTvProvinceName.setText(province.getProvinceName());
        holder.mTvNumberStore.setText(province.getNumberPlaceText());
    }

    @Override
    public int getItemCount() {
        return mProvinces.size();
    }

    /**
     * ItemProvinceVH class
     */
    static class ItemProvinceVH extends RecyclerView.ViewHolder {
        private final TextView mTvProvinceName;
        private final TextView mTvNumberStore;

        ItemProvinceVH(View itemView, final OnPlaceDetailProvinceListener onPlaceDetailProvinceListener) {
            super(itemView);
            mTvProvinceName = (TextView) itemView.findViewById(R.id.tvProvinceName);
            mTvNumberStore = (TextView) itemView.findViewById(R.id.tvNumberStore);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPlaceDetailProvinceListener != null) {
                        onPlaceDetailProvinceListener.onProvinceClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnPlaceDetailProvinceListener interface
     */
    public interface OnPlaceDetailProvinceListener {
        void onProvinceClick(int position);
    }
}
