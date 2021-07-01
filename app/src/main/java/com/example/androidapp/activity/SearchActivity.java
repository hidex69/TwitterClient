//SearchActivity.java

package com.example.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapp.R;
import com.example.androidapp.adapter.UserAdapter;
import com.example.androidapp.network.HttpClient;
import com.example.androidapp.pojo.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.Collection;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter ;
    private Toolbar toolbar;
    private EditText editText;
    private Button button;
    private HttpClient connection = new HttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initRecyclerView();

        toolbar = findViewById(R.id.toolbar);
        editText = toolbar.findViewById(R.id.query_edit_text);
        button = toolbar.findViewById(R.id.search_button);

        button.setOnClickListener(v -> searchUsers());

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchUsers();
                return true;
            }
            return false;
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserAdapter.onUserClickListener onUserClickListener = user -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.USER_ID, user.getId());
            startActivity(intent);
        };
        userAdapter = new UserAdapter(onUserClickListener);
        recyclerView.setAdapter(userAdapter);
    }

    private void searchUsers() {
        final String query = editText.getText().toString();
        if (query.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_LONG).show();
            return;
        }

        new SearchUserTask().execute(query);
    }

    @SuppressLint("StaticFieldLeak")
    private class SearchUserTask extends AsyncTask<String, Integer, Collection<User>> {

        @Override
        protected Collection<User> doInBackground(String... ids) {
            try {
                String query = ids[0];
                return connection.readUsers(query);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Collection<User> users) {
            userAdapter.clearList();
            userAdapter.addToList(users);
        }
    }
}
