package com.gloomy.fastfood.models;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@Getter
public class Comment {
    private int commentId;
    private User user;
    private String content;
    private Timestamp postTime;
    private List<Comment> postReplies;
}
