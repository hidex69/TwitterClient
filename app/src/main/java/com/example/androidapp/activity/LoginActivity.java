//LoginActivity.java

package com.example.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Application;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapp.R;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aService;

public class LoginActivity extends AppCompatActivity {
    TwitterLoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)
        );

        TwitterConfig twitterConfig=new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .debug(true)
                .build();

        Twitter.initialize(twitterConfig);
        setContentView(R.layout.auth_activity);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();

                loginMethod(session);
            }

            @Override
            public void failure(TwitterException exception) {
                System.out.println("OWOWOWOWOWO");
                Toast.makeText(getApplicationContext(),"Login fail",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginMethod(TwitterSession twitterSession){
        String userName=twitterSession.getUserName();

        Intent intent= new Intent(this, MainActivity.class);
        intent.putExtra("username",userName);
        intent.putExtra("user_id", String.valueOf(twitterSession.getUserId()));

        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
