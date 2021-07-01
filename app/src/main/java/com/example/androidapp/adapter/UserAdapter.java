//UserAdapter.java

package com.example.androidapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapp.R;
import com.example.androidapp.pojo.Tweet;
import com.example.androidapp.pojo.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private onUserClickListener onUserClickListener;

    public UserAdapter(UserAdapter.onUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void addToList(Collection<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearList() {
        userList.clear();
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView nick;
        private ImageView img;

        public UserViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.user_text_name_view);
            nick = view.findViewById(R.id.user_text_nick_view);
            img = view.findViewById(R.id.profile_image_view);
            view.setOnClickListener(v -> {
                User user = userList.get(getLayoutPosition());
                onUserClickListener.onUserClick(user);
            });
        }

        public void bind(User user) {
            name.setText(user.getName());
            nick.setText(user.getNick());
            Picasso.with(itemView.getContext()).load(user.getUrl()).into(img);
        }
    }

    public interface onUserClickListener {
        void onUserClick(User user);
    }
}
