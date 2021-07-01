//Parser.java

package com.example.androidapp.network;

import android.util.Log;
import com.example.androidapp.pojo.Tweet;
import com.example.androidapp.pojo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Parser {
    public User getUser(String response) throws JSONException {
        JSONObject user = new JSONObject(response);
        return new User(user.getString("name"),
                "@" + user.getString("screen_name"),
                user.getString("id_str"),
                user.getString("description"),
                user.getString("location"),
                user.getInt("friends_count"),
                user.getInt("followers_count"),
                user.getString("profile_image_url_https"));
    }

    public Collection<Tweet> getTweets(String response) throws JSONException {
        JSONArray tweets = new JSONArray(response);
        ArrayList<Tweet> tweetsResult = new ArrayList<>();

        for (int i = 0; i < tweets.length(); i++) {
            JSONObject tweetJSON = tweets.getJSONObject(i);
            Tweet tweet = new Tweet(
                    getUser(tweetJSON.getString("user")),
                    tweetJSON.getInt("retweet_count"),
                    tweetJSON.getInt("favorite_count"),
                    tweetJSON.getString("text"),
                    tweetJSON.getString("created_at"),
                    tweetJSON.getString("id_str"),
                    getImgUrl(tweetJSON));
            tweetsResult.add(tweet);
        }

        return tweetsResult;
    }

    public Collection<User> getUsers(String response) throws JSONException {
        JSONArray users = new JSONArray(response);
        ArrayList<User> usersResult = new ArrayList<>();

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJSON = users.getJSONObject(i);
            User user = new User(
                    userJSON.getString("name"),
                    "@" + userJSON.getString("screen_name"),
                    userJSON.getString("id_str"),
                    userJSON.getString("description"),
                    userJSON.getString("location"),
                    userJSON.getInt("friends_count"),
                    userJSON.getInt("followers_count"),
                    userJSON.getString("profile_image_url_https")
            );
            usersResult.add(user);
        }

        return usersResult;
    }

    private String getImgUrl(JSONObject tweetJson) throws JSONException {
        JSONObject entities = tweetJson.getJSONObject("entities");
        JSONArray mediaArray = entities.has("media") ? entities.getJSONArray("media") : null;
        JSONObject firstMedia = mediaArray != null ? mediaArray.getJSONObject(0) : null;
        return firstMedia != null ? firstMedia.getString("media_url") : null;
    }
}
