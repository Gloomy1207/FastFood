package com.gloomy.fastfood.mvp.presenters.detail.store;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.LikeResponse;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.StoreAddress;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.detail.store.IStoreDetailView;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailActivity;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailPagerAdapter;
import com.gloomy.fastfood.observer.StoreDetailObserver;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.gloomy.fastfood.widgets.dialog.rating.RatingDialog;
import com.gloomy.fastfood.widgets.dialog.rating.RatingDialog_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EBean
public class StoreDetailPresenter extends BasePresenter implements CustomTextInputLayout.OnTextInputLayoutListener, RatingDialog.OnRatingDialogListener {

    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
    @StringArrayRes(R.array.store_detail_tab_titles)
    String[] mTitles;
    @Setter
    @Accessors(prefix = "m")
    private IStoreDetailView mView;
    @Setter
    @Accessors(prefix = "m")
    private Store mStore;
    private boolean mIsSendingComment;

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(mStore.getStoreName());
        headerBar.setRightButtonVisibility(View.VISIBLE);
        headerBar.setImageResourceRightButton(R.drawable.ic_gallery_primary);
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackClick();
            }

            @Override
            public void onRightButtonClick() {
                mView.onViewImagesClick();
            }
        });
    }

    public void initButtonFavorite(FloatingActionButton btnFavorite) {
        btnFavorite.setSelected(mStore.isFavorite());
    }

    public void initCommentLayout(CustomTextInputLayout commentLayout) {
        commentLayout.setHint(getString(R.string.comment_topic));
        commentLayout.setOnTextInputLayoutListener(this);
    }

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout) {
        if (getContext() instanceof StoreDetailActivity) {
            viewPager.setAdapter(new StoreDetailPagerAdapter(((StoreDetailActivity) getContext()).getFragmentManager(), mStore));
            viewPager.setOffscreenPageLimit(StoreDetailPagerAdapter.NUM_PAGE);
            tabLayout.setupWithViewPager(viewPager);
            TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mTitles, mContext);
        }
    }

    public void setDataForViews() {
        mView.onSetStoreName(mStore.getStoreName());
        mView.onSetStoreTime(String.format("%s - %s", mSimpleDateFormat.format(mStore.getOpenTime()), mSimpleDateFormat.format(mStore.getCloseTime())));
        StringBuilder builder = new StringBuilder();
        if (mStore.getStoreAddress() != null) {
            StoreAddress address = mStore.getStoreAddress();
            builder.append(address.getAddressName()).append(", ");
            if (address.getProvince() != null) {
                builder.append(address.getProvince().getProvinceName()).append(", ");
                if (address.getProvince().getCity() != null) {
                    builder.append(address.getProvince().getCity().getCityName());
                }
            }
        }
        int lastIndexOfSplitAddress = builder.lastIndexOf(" ,");
        if (lastIndexOfSplitAddress != -1) {
            builder.delete(lastIndexOfSplitAddress, lastIndexOfSplitAddress + 2);
        }
        mView.onSetStoreAddress(builder.toString());
        mView.onSetStoreImage(mStore.getMainImage());
        mView.onSetNumberStars(String.valueOf(mStore.getAverageRating()));
        mView.onSetNumberRating(mStore.getNumberRating());
    }

    public void onFavoriteClick(final FloatingActionButton btnFavorite) {
        if (!AuthSession.isLogIn()) {
            mView.onNotLogin();
            return;
        }
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        btnFavorite.setSelected(!btnFavorite.isSelected());
        ApiRequest.getInstance().favoriteStore(mStore.getStoreId(), new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response == null || response.body() == null) {
                    btnFavorite.setSelected(!btnFavorite.isSelected());
                    return;
                }
                if (!response.body().isStatus()) {
                    btnFavorite.setSelected(!btnFavorite.isSelected());
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                btnFavorite.setSelected(!btnFavorite.isSelected());
                // No-op
            }
        });
    }

    @Override
    public void onSendClick(String message) {
        if (!AuthSession.isLogIn()) {
            mView.onNotLogin();
            return;
        }
        if (TextUtils.isEmpty(message)) {
            mView.onEmptyComment();
            return;
        }
        if (mIsSendingComment) {
            mView.onSendingComment();
            return;
        }
        mIsSendingComment = true;
        Comment comment = Comment.builder()
                .content(message)
                .postTime(new Timestamp(System.currentTimeMillis()))
                .user(AuthSession.getInstance().getAuthLogin().getUser())
                .status(Comment.CommentStatus.LOADING)
                .build();
        StoreDetailObserver.post(comment);
        mView.onSendComment();
    }

    public void onSendCommentComplete() {
        mIsSendingComment = false;
    }

    public void onRatingClick(FragmentManager fragmentManager) {
        RatingDialog dialog = RatingDialog_.builder().mStore(mStore).build();
        dialog.setOnRatingDialogListener(this);
        dialog.show(fragmentManager, RatingDialog.class.getSimpleName());
    }

    @Override
    public void onRatingSuccess(String message) {
        mView.onDismissProgressDialog();
        mView.onRatingComplete(message);
    }

    @Override
    public void onRatingFailure() {
        mView.onDismissProgressDialog();
        mView.onRatingFailure();
    }

    @Override
    public void onNotLogin() {
        mView.onNotLogin();
    }

    @Override
    public void onNoInternetConnection() {
        mView.onNoInternetConnection();
    }
}
