package com.gloomy.fastfood.ui.views.main.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.ItemSetting;
import com.gloomy.fastfood.ui.views.BaseAdapter;

import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
public class SettingAdapter extends BaseAdapter<SettingAdapter.ItemSettingVH> {
    private final List<ItemSetting> mItemSettings;
    private final OnSettingListener mOnSettingListener;

    public SettingAdapter(@NonNull Context mContext, List<ItemSetting> itemSettings, OnSettingListener onSettingListener) {
        super(mContext);
        mItemSettings = itemSettings;
        mOnSettingListener = onSettingListener;
    }

    @Override
    public ItemSettingVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_setting, parent, false);
        return new ItemSettingVH(view);
    }

    @Override
    public void onBindViewHolder(ItemSettingVH holder, int position) {
        ItemSetting setting = mItemSettings.get(position);
        holder.mTvTitle.setText(setting.getTitle());
        setOnSettingClick(setting.getType(), holder.mLayoutParent);
    }

    private void setOnSettingClick(int type, RelativeLayout layoutParent) {
        switch (type) {
            case ItemSetting.SettingItemType.LOGOUT:
                layoutParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnSettingListener != null) {
                            mOnSettingListener.onLogoutClick();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemSettings.size();
    }

    /**
     * ViewHolder for item setting
     */
    static class ItemSettingVH extends RecyclerView.ViewHolder {
        private final TextView mTvTitle;
        private final RelativeLayout mLayoutParent;

        ItemSettingVH(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mLayoutParent = (RelativeLayout) itemView.findViewById(R.id.layoutParent);
        }
    }

    /**
     * OnSettingListener interface
     */
    public interface OnSettingListener {
        void onLogoutClick();
    }
}
