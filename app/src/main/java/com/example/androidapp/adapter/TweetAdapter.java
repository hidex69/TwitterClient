//TweetAdapter.java

package com.example.androidapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapp.R;
import com.example.androidapp.pojo.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {
    private static final String TWITTER_RESPONSE_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    private static final String MONTH_DAY_FORMAT = "MMM d";
    private List<Tweet> tweetList = new ArrayList<>();

    public void addToList(Collection<Tweet> tweets) {
        tweetList.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearList() {
        tweetList.clear();
        notifyDataSetChanged();
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item_view, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.bind(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView nickTextView;
        private TextView creationDateTextView;
        private TextView contentTextView;
        private TextView retweetsTextView;
        private TextView likesTextView;
        private ImageView tweetImgView;
        private ImageView userImgView;

        public TweetViewHolder (View view) {
            super(view);
            nameTextView = view.findViewById(R.id.author_name_text_view);
            nickTextView = view.findViewById(R.id.author_nick_text_view);
            creationDateTextView = view.findViewById(R.id.author_data_text_view);
            contentTextView = view.findViewById(R.id.tweet_content_text_view);
            retweetsTextView = view.findViewById(R.id.retweets_counter_text_view);
            likesTextView = view.findViewById(R.id.heart_counter_text_view);
            tweetImgView = view.findViewById(R.id.tweet_image_view);
            userImgView = view.findViewById(R.id.profile_image_view);
        }

        public void bind(Tweet tweet) {
            nameTextView.setText(tweet.getUser().getName());
            nickTextView.setText(tweet.getUser().getNick());
            creationDateTextView.setText(tweet.getDate());
            contentTextView.setText(tweet.getText());
            retweetsTextView.setText(String.valueOf(tweet.getRetweets()));
            likesTextView.setText(String.valueOf(tweet.getLikes()));

            Picasso.with(itemView.getContext()).load(tweet.getUrl()).into(tweetImgView);
            Picasso.with(itemView.getContext()).load(tweet.getUser().getUrl()).into(userImgView);
        }
    }
}
