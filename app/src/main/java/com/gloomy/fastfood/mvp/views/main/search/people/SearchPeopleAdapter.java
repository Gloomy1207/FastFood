package com.gloomy.fastfood.mvp.views.main.search.people;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public class SearchPeopleAdapter extends BaseAdapter<SearchPeopleAdapter.ItemSearchPeopleVH> {
    private List<User> mUsers;
    private OnSearchPeopleListener mOnSearchPeopleListener;

    public SearchPeopleAdapter(@NonNull Context mContext, List<User> users, OnSearchPeopleListener onSearchPeopleListener) {
        super(mContext);
        mUsers = users;
        mOnSearchPeopleListener = onSearchPeopleListener;
    }

    @Override
    public ItemSearchPeopleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_search_people, parent, false);
        return new ItemSearchPeopleVH(view, mOnSearchPeopleListener);
    }

    @Override
    public void onBindViewHolder(ItemSearchPeopleVH holder, int position) {
        if (position == 0) {
            holder.mViewDivider.setVisibility(View.GONE);
        } else {
            holder.mViewDivider.setVisibility(View.VISIBLE);
        }
        User user = mUsers.get(position);
        Picasso.with(getContext())
                .load(user.getAvatar())
                .into(holder.mImgAvatar);
        holder.mTvPoint.setText(getContext().getString(R.string.text_point, user.getPoint()));
        holder.mTvUsername.setText(user.getUsername());
        if (TextUtils.isEmpty(user.getFullname()) && TextUtils.isEmpty(user.getDescription())) {
            holder.mTvInformation.setVisibility(View.GONE);
        } else {
            holder.mTvInformation.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(user.getFullname()) && !TextUtils.isEmpty(user.getDescription())) {
            String information = String.format("%s - %s", user.getFullname(), user.getDescription());
            SpannableString spannableString = new SpannableString(information);
            int startIndex = information.indexOf(user.getFullname());
            int endIndex = startIndex + user.getFullname().length() + 3;
            spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTvInformation.setText(spannableString);
        } else {
            if (TextUtils.isEmpty(user.getFullname())) {
                holder.mTvInformation.setText(user.getDescription());
                holder.mTvInformation.setTextColor(Color.DKGRAY);
            } else {
                holder.mTvInformation.setText(user.getFullname());
                holder.mTvInformation.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * ItemSearchPeopleVH class
     */
    static class ItemSearchPeopleVH extends RecyclerView.ViewHolder {
        private final CircleImageView mImgAvatar;
        private final View mViewDivider;
        private final TextView mTvPoint;
        private final TextView mTvUsername;
        private final TextView mTvInformation;

        ItemSearchPeopleVH(View itemView, final OnSearchPeopleListener onSearchPeopleListener) {
            super(itemView);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mViewDivider = itemView.findViewById(R.id.viewDivider);
            mTvPoint = (TextView) itemView.findViewById(R.id.tvPoint);
            mTvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            mTvInformation = (TextView) itemView.findViewById(R.id.tvInformation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSearchPeopleListener != null) {
                        onSearchPeopleListener.onUserClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnSearchPeopleListener interface
     */
    public interface OnSearchPeopleListener {
        void onUserClick(int position);
    }
}
