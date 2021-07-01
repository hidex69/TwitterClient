//User.java

package com.example.androidapp.pojo;

import androidx.annotation.Nullable;

import java.util.Objects;

public class User {
    private final String name;
    private final String nick;
    private final String id;
    private final String description;
    private final String location;
    private final int following;
    private final int followers;
    private final String url;

    public User(String name, String nick, String id, String description, String location, int following, int followers, String url) {
        this.name = name;
        this.nick = nick;
        this.id = id;
        this.description = description;
        this.location = location;
        this.following = following;
        this.followers = followers;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getFollowing() {
        return following;
    }

    public int getFollowers() {
        return followers;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nick);
    }
}
