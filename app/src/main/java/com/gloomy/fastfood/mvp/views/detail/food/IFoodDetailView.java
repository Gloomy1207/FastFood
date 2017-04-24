package com.gloomy.fastfood.mvp.views.detail.food;

import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
public interface IFoodDetailView extends IBaseView {
    void onNoFoodData();

    void onBackPressed();

    void setImageFood(String mainImage);

    void setFoodName(String foodName);

    void setRatingBar(float rating);

    void setDescription(String description);

    void setRecipe(String recipe);
}
