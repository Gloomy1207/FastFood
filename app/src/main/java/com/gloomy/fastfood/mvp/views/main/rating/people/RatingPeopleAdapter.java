package com.gloomy.fastfood.mvp.views.main.rating.people;

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
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/04/2017.
 */
public class RatingPeopleAdapter extends BaseAdapter<RatingPeopleAdapter.ItemRatingPeopleVH> {

    private final List<User> mUsers;
    private final OnRatingPeopleListener mOnRatingPeopleListener;

    public RatingPeopleAdapter(@NonNull Context mContext, List<User> users, OnRatingPeopleListener onRatingPeopleListener) {
        super(mContext);
        mUsers = users;
        mOnRatingPeopleListener = onRatingPeopleListener;
    }

    @Override
    public ItemRatingPeopleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_rating_people, parent, false);
        return new ItemRatingPeopleVH(view, mOnRatingPeopleListener);
    }

    @Override
    public void onBindViewHolder(ItemRatingPeopleVH holder, int position) {
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
        holder.mTvUsername.setCompoundDrawablePadding((int) ScreenUtil.convertDpiToPixel(getContext(), 5));
        switch (position) {
            case 0:
                holder.mTvUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_golden_crow, 0);
                holder.mViewDivider.setVisibility(View.GONE);
                break;
            case 1:
                holder.mTvUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_silver_crow, 0);
                break;
            case 2:
                holder.mTvUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_copper_crow, 0);
                break;
            default:
                holder.mTvUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * ViewHolder for item RatingPeople
     */
    static class ItemRatingPeopleVH extends RecyclerView.ViewHolder {
        private final CircleImageView mImgAvatar;
        private final TextView mTvPoint;
        private final TextView mTvUsername;
        private final TextView mTvInformation;
        private final View mViewDivider;

        ItemRatingPeopleVH(View itemView, final OnRatingPeopleListener onRatingPeopleListener) {
            super(itemView);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mViewDivider = itemView.findViewById(R.id.viewDivider);
            mTvPoint = (TextView) itemView.findViewById(R.id.tvPoint);
            mTvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            mTvInformation = (TextView) itemView.findViewById(R.id.tvInformation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRatingPeopleListener != null) {
                        onRatingPeopleListener.onItemRatingPeopleClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnRatingPeopleListener interface
     */
    public interface OnRatingPeopleListener {
        void onItemRatingPeopleClick(int position);
    }
}
