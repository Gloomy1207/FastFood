package com.gloomy.fastfood.ui.views.main.post;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.post.PostPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_post)
public class PostFragment extends BaseFragment implements IViewPost {

    @Bean
    PostPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }
}
