package com.gloomy.fastfood.widgets.dialog.rating;

import android.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.PlaceRatingResponse;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.RatingType;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.utils.NetworkUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 26/04/2017.
 */
@EFragment(R.layout.layout_rating_dialog)
public class RatingDialog extends DialogFragment implements RatingAdapter.OnRatingListener {
    private static final int NUM_COLUMN = 2;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @ViewById(R.id.tvTitle)
    TextView mTvTitle;

    @FragmentArg
    Store mStore;

    @Setter
    @Accessors(prefix = "m")
    private OnRatingDialogListener mOnRatingDialogListener;

    private List<RatingType> mRatingTypes = new ArrayList<>();

    @AfterViews
    void afterView() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        if (mStore.getStoreType() != null && !mStore.getStoreType().getRatingTypes().isEmpty()) {
            mRatingTypes.addAll(mStore.getStoreType().getRatingTypes());
            if (mRatingTypes.size() % 2 == 1) {
                mRatingTypes.get(mRatingTypes.size() - 1).setLarge(true);
            }
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), NUM_COLUMN);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == RatingAdapter.TITLE_ITEM_OFFSET) {
                        return NUM_COLUMN;
                    } else if (position == mRatingTypes.size() + 1) {
                        return NUM_COLUMN;
                    } else if (mRatingTypes.get(position - RatingAdapter.RATING_ITEM_OFFSET).isLarge()) {
                        return NUM_COLUMN;
                    }
                    return 1;
                }
            });
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(new RatingAdapter(getActivity(), mRatingTypes, this, getString(R.string.rating_title_text, mStore.getStoreName())));
            mRecyclerView.setVisibility(View.VISIBLE);
            mLayoutEmpty.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mTvTitle.setText(mStore.getStoreName());
            mTvEmptyMessage.setText(R.string.rating_type_empty);
        }
    }

    @Override
    public void onSubmitClick() {
        dismiss();
        if (!AuthSession.isLogIn()) {
            if (mOnRatingDialogListener != null) {
                mOnRatingDialogListener.onNotLogin();
            }
            return;
        }
        if (!NetworkUtil.isNetworkAvailable(getActivity())) {
            if (mOnRatingDialogListener != null) {
                mOnRatingDialogListener.onNoInternetConnection();
            }
            return;
        }
        ApiRequest.getInstance().ratingPlace(mStore.getStoreId(), mRatingTypes, new Callback<PlaceRatingResponse>() {
            @Override
            public void onResponse(Call<PlaceRatingResponse> call, Response<PlaceRatingResponse> response) {
                if (getActivity() instanceof IBaseView) {
                    ((IBaseView) getActivity()).onDismissProgressDialog();
                }
                if (response == null || response.body() == null) {
                    return;
                }
                PlaceRatingResponse ratingResponse = response.body();
                if (mOnRatingDialogListener != null) {
                    mOnRatingDialogListener.onRatingSuccess(ratingResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PlaceRatingResponse> call, Throwable t) {
                if (getActivity() instanceof IBaseView) {
                    ((IBaseView) getActivity()).onDismissProgressDialog();
                }
                if (mOnRatingDialogListener != null) {
                    mOnRatingDialogListener.onRatingFailure();
                }
            }
        });
    }

    /**
     * OnRatingDialogListener interface
     */
    public interface OnRatingDialogListener {
        void onRatingSuccess(String message);

        void onRatingFailure();

        void onNotLogin();

        void onNoInternetConnection();
    }
}
