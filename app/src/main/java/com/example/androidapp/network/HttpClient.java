//HttpClient.java

package com.example.androidapp.network;

import android.util.Log;
import com.example.androidapp.pojo.Tweet;
import com.example.androidapp.pojo.User;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aHeaders;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

public class HttpClient {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String GET = "Get";
    private static final String EXTENDED_MODE = "&tweet_mode=extended";
    private Parser parser = new Parser();

    public User readUserInfo(String userId) throws IOException, JSONException {
        String request = "https://api.twitter.com/1.1/users/show.json?user_id=" + userId;

        return parser.getUser(getResponse(request));
    }


    public Collection<Tweet> readTweets(String userId) throws IOException, JSONException {
        String request = "https://api.twitter.com/1.1/statuses/user_timeline.json?user_id=" + userId;

        String response = getResponse(request);
        return parser.getTweets(response);
    }

    public Collection<User> readUsers(String query) throws JSONException, IOException {
        String request = "https://api.twitter.com/1.1/users/search.json?q=" + query;
        String response = getResponse(request);
        return parser.getUsers(response);
    }

    private String getAuthHeader(String url) {
        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        return new OAuth1aHeaders().getAuthorizationHeader(authConfig, session.getAuthToken(), null,
                "Get", url, null);
    }

    private String getJSON(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        in.close();

        return stringBuilder.toString();
    }

    private String getResponse(String request) throws IOException {
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String authHeader = getAuthHeader(request);
        connection.setRequestProperty(HEADER_AUTHORIZATION, authHeader);

        connection.connect();

        InputStream in;
        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            in = connection.getErrorStream();
        } else {
            in = connection.getInputStream();
        }

        Log.d("uwu", "owo");

        return getJSON(in);
    }
}
