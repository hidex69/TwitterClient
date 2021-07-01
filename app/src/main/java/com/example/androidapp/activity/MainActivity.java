//MainActivity.java

package com.example.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapp.R;
import com.example.androidapp.adapter.TweetAdapter;
import com.example.androidapp.network.HttpClient;
import com.example.androidapp.pojo.Tweet;
import com.example.androidapp.pojo.User;
import com.squareup.picasso.Picasso;
import org.json.JSONException;

import java.io.IOException;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";
    private TextView userId;
    private TextView userName;
    private TextView userNick;
    private TextView userDescription;
    private TextView userLocation;
    private TextView userFollowing;
    private TextView userFollowers;
    private RecyclerView tweetsRecyclerView;

    private HttpClient connection;

    private Toolbar toolbar;
    private TweetAdapter tweetAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        return true;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userId = getIntent().getStringExtra(USER_ID);
//        String userId = "284168384";

        userName = findViewById(R.id.user_text_name_view);
        userNick = findViewById(R.id.user_text_nick_view);
        userDescription = findViewById(R.id.user_text_description_view);
        userLocation = findViewById(R.id.user_text_location_view);
        userFollowing = findViewById(R.id.following_count_text_view);
        userFollowers = findViewById(R.id.followers_count_text_view);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecycler();

        connection = new HttpClient();
        loadUser(userId);

        loadTweets(userId);
    }

    private void initRecycler() {
        tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ViewCompat.setNestedScrollingEnabled(tweetsRecyclerView, false);
        tweetsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        tweetAdapter = new TweetAdapter();
        tweetsRecyclerView.setAdapter(tweetAdapter);
    }

    private void displayUser(User user) {
        userName.setText(user.getName());
        userNick.setText(user.getNick());
        userDescription.setText(user.getDescription());
        userLocation.setText(user.getLocation());
        userFollowing.setText(String.valueOf(user.getFollowing()));
        userFollowers.setText(String.valueOf(user.getFollowers()));

        ImageView view = findViewById(R.id.user_image_view);
        Picasso.with(this).load(user.getUrl()).into(view);

        getSupportActionBar().setTitle(user.getName());

    }

    private void loadUser(final String userId) {
        new UserInfoAsyncTask().execute(userId);
    }

    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<String, Integer, User> {

        @Override
        protected User doInBackground(String... ids) {
            try {
                String user_id = ids[0];
                return connection.readUserInfo(user_id);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            displayUser(user);
        }
    }

    private void loadTweets(final String userId) {
        new TweetsAsyncTask().execute(userId);
    }

    @SuppressLint("StaticFieldLeak")
    private class TweetsAsyncTask extends AsyncTask<String, Integer, Collection<Tweet>> {

        @Override
        protected Collection<Tweet> doInBackground(String... ids) {
            try {
                String user_id = ids[0];

                return connection.readTweets(user_id);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Collection<Tweet> tweets) {
            tweetAdapter.addToList(tweets);
        }
    }
}