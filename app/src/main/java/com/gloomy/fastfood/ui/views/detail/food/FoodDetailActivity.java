package com.gloomy.fastfood.ui.views.detail.food;

import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.detail.food.FoodDetailPresenter;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
@EActivity(R.layout.fragment_detail_food)
public class FoodDetailActivity extends BaseActivity implements IFoodDetailView {

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.imgFood)
    ImageView mImgFood;

    @ViewById(R.id.tvFoodName)
    TextView mTvFoodName;

    @ViewById(R.id.ratingBar)
    RatingBar mRatingBar;

    @ViewById(R.id.tvDescription)
    TextView mTvDescription;

    @ViewById(R.id.tvRecipe)
    TextView mTvRecipe;

    @ViewById(R.id.scrollView)
    NestedScrollView mNestedScrollView;

    @ViewById(R.id.cardView)
    CardView mCardView;

    @ViewById(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @Extra
    Parcelable mFoodParcelable;

    @Bean
    FoodDetailPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initValueForViews(mFoodParcelable);
        mPresenter.initHeaderBar(mHeaderBar);
        mNestedScrollView.setFillViewport(true);
        mPresenter.initCardView(mCardView, mAppBarLayout);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnection();
    }

    @Override
    public void onNoFoodData() {
        finish();
        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @Override
    public void setImageFood(String mainImage) {
        Picasso.with(this)
                .load(mainImage)
                .into(mImgFood);
    }

    @Override
    public void setFoodName(String foodName) {
        mTvFoodName.setText(foodName);
    }

    @Override
    public void setRatingBar(float rating) {
        mRatingBar.setRating(rating);
    }

    @Override
    public void setDescription(String description) {
        mTvDescription.setText(description);
    }

    @Override
    public void setRecipe(String recipe) {
        mTvRecipe.setText(recipe);
    }

    @Click(R.id.btnViewImage)
    void onViewImageClick() {
        mPresenter.viewImages();
    }
}
