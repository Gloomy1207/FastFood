package com.gloomy.fastfood.ui.presenters.main.post;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.post.IViewPost;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class PostPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IViewPost mView;
}
