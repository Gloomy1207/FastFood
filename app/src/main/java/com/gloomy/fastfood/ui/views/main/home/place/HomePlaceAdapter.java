package com.gloomy.fastfood.ui.views.main.home.place;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Place;
import com.gloomy.fastfood.ui.views.BaseAdapter;

import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 12/04/2017.
 */
public class HomePlaceAdapter extends BaseAdapter {

    private List<Place> mPlaces;
    private OnHomePlaceListener mOnHomePlaceListener;

    public HomePlaceAdapter(@NonNull Context mContext, List<Place> places, OnHomePlaceListener onHomePlaceListener) {
        super(mContext);
        mPlaces = places;
        mOnHomePlaceListener = onHomePlaceListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mPlaces.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Place.PlaceType.CITY) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_place_city, parent, false);
            return new ItemCityVH(view, mOnHomePlaceListener);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_place_province, parent, false);
            return new ItemProvinceVH(view, mOnHomePlaceListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemCityVH) {
            onBindCityVH((ItemCityVH) holder, position);
        } else if (holder instanceof ItemProvinceVH) {
            onBindProvinceVH((ItemProvinceVH) holder, position);
        }
    }

    private void onBindCityVH(ItemCityVH holder, int position) {
        Place.PlaceCity city = (Place.PlaceCity) mPlaces.get(position);
        holder.mTvCityName.setText(city.getCity().getCityName());
    }

    private void onBindProvinceVH(ItemProvinceVH holder, int position) {
        Place.PlaceProvince province = (Place.PlaceProvince) mPlaces.get(position);
        holder.mTvProvinceName.setText(province.getProvince().getProvinceName());
        holder.mTvNumberStore.setText(province.getProvince().getNumberPlaceText());
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    /**
     * ViewHolder for item City
     */
    private static class ItemCityVH extends RecyclerView.ViewHolder {
        private final TextView mTvCityName;

        ItemCityVH(View itemView, final OnHomePlaceListener onHomePlaceListener) {
            super(itemView);
            mTvCityName = (TextView) itemView.findViewById(R.id.tvCityName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onHomePlaceListener != null) {
                        onHomePlaceListener.onItemCityClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * ViewHolder for item province
     */
    private static class ItemProvinceVH extends RecyclerView.ViewHolder {

        private final TextView mTvProvinceName;
        private final TextView mTvNumberStore;

        ItemProvinceVH(View itemView, final OnHomePlaceListener onHomePlaceListener) {
            super(itemView);
            mTvProvinceName = (TextView) itemView.findViewById(R.id.tvProvinceName);
            mTvNumberStore = (TextView) itemView.findViewById(R.id.tvNumberStore);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onHomePlaceListener != null) {
                        onHomePlaceListener.onItemProvinceClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnHomePlaceListener interface
     */
    public interface OnHomePlaceListener {
        void onItemCityClick(int position);

        void onItemProvinceClick(int position);
    }
}
