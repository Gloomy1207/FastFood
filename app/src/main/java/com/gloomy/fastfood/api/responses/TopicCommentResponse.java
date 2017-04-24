package com.gloomy.fastfood.api.responses;

import com.gloomy.fastfood.mvp.models.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 23-Apr-17.
 */
@Getter
public class TopicCommentResponse {
    @SerializedName("content")
    private List<Comment> comments;
    @SerializedName("number")
    private int currentPage;
    @SerializedName("last")
    private boolean isLast;
}
