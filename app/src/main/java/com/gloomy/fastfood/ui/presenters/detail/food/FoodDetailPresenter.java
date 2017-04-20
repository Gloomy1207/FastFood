package com.gloomy.fastfood.ui.presenters.detail.food;

import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.detail.food.IFoodDetailView;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;
import org.parceler.Parcels;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
@EBean
public class FoodDetailPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IFoodDetailView mView;

    @DimensionPixelOffsetRes(R.dimen.detail_food_margin_left_right)
    int mMarginLeftRight;

    @DimensionPixelOffsetRes(R.dimen.detail_food_margin_top)
    int mMarginTop;

    private Food mFood;

    public void initValueForViews(Parcelable foodParcelable) {
        if (foodParcelable == null) {
            mView.onNoFoodData();
            return;
        }
        mFood = Parcels.unwrap(foodParcelable);
        mView.setImageFood(mFood.getMainImage());
        mView.setFoodName(mFood.getFoodName());
        mView.setRatingBar(mFood.getRating());
        mView.setDescription(mFood.getDescription());
        mView.setRecipe(mFood.getRecipe());
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(mFood.getFoodName());
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackPressed();
            }

            @Override
            public void onRightButtonClick() {
                // No-op
            }
        });
    }

    public void initCardView(final CardView cardView, AppBarLayout appBarLayout) {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            }
        });

    }
}
