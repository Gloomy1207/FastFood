package com.gloomy.fastfood.mvp.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 13/04/2017.
 */
public abstract class SearchStoreItem {

    /**
     * SearchStoreItemType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SearchStoreItemType.TITLE, SearchStoreItemType.STORE})
    public @interface SearchStoreItemType {
        int TITLE = 1;
        int STORE = 2;
    }

    public abstract int getType();

    /**
     * StoreItem class
     */
    @Builder
    @Getter
    public static class StoreItem extends SearchStoreItem {
        private Store store;

        @Override
        public int getType() {
            return SearchStoreItemType.STORE;
        }
    }

    /**
     * TitleItem class
     */
    @Builder
    @Getter
    public static class TitleItem extends SearchStoreItem {
        private String title;

        @Override
        public int getType() {
            return SearchStoreItemType.TITLE;
        }
    }
}
