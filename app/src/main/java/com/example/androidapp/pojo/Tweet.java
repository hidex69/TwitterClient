//Tweet.java

package com.example.androidapp.pojo;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Tweet {
    private  final User user;
    private final int likes;
    private final int retweets;
    private final String text;
    private final String date;
    private final String id;
    private final String url;

    public Tweet(User user, int likes, int retweets, String text, String date, String id, String url) {
        this.user = user;
        this.likes = likes;
        this.retweets = retweets;
        this.text = text;
        this.date = date;
        this.id = id;
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public int getLikes() {
        return likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public String getUrl() {
        return url;
    }
}
