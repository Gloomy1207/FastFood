package com.gloomy.fastfood.mvp.views.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.ItemSetting;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.LocaleUtil;
import com.gloomy.fastfood.utils.ScreenUtil;

import java.util.List;
import java.util.Locale;

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
        if (setting.getType() == ItemSetting.SettingItemType.LANGUAGE) {
            onBindLanguageItem(holder);
        }
        setOnSettingClick(setting.getType(), holder.mLayoutParent);
    }

    private void onBindLanguageItem(ItemSettingVH holder) {
        holder.mImgNavigateNext.setVisibility(View.GONE);
        holder.mTvValue.setVisibility(View.VISIBLE);
        Locale locale = LocaleUtil.getInstance().getCurrentLocale(getContext());
        if (locale.getLanguage().equalsIgnoreCase("vi")) {
            holder.mTvValue.setText(getContext().getString(R.string.setting_language_vietnamese));
            holder.mTvValue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flag_vietnamese, 0, 0, 0);
        } else {
            holder.mTvValue.setText(getContext().getString(R.string.setting_language_english));
            holder.mTvValue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flag_us, 0, 0, 0);
        }
        holder.mTvValue.setCompoundDrawablePadding((int) ScreenUtil.convertDpiToPixel(getContext(), 10));
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
            case ItemSetting.SettingItemType.UPDATE_PROFILE:
                layoutParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnSettingListener != null) {
                            mOnSettingListener.onUpdateProfileClick();
                        }
                    }
                });
                break;
            case ItemSetting.SettingItemType.LANGUAGE:
                layoutParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnSettingListener != null) {
                            mOnSettingListener.onLanguageClick();
                        }
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return mItemSettings.size();
    }

    /**
     * OnSettingListener interface
     */
    public interface OnSettingListener {
        void onLogoutClick();

        void onUpdateProfileClick();

        void onLanguageClick();
    }

    /**
     * ViewHolder for item setting
     */
    static class ItemSettingVH extends RecyclerView.ViewHolder {
        private final TextView mTvTitle;
        private final RelativeLayout mLayoutParent;
        private final ImageView mImgNavigateNext;
        private final TextView mTvValue;

        ItemSettingVH(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mLayoutParent = (RelativeLayout) itemView.findViewById(R.id.layoutParent);
            mImgNavigateNext = (ImageView) itemView.findViewById(R.id.imgNavigateNext);
            mTvValue = (TextView) itemView.findViewById(R.id.tvValue);
        }
    }
}
