package com.thitiphat.feedfold.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.thitiphat.feedfold.R;
import com.thitiphat.feedfold.adapter.BookmarkAdapter;
import com.thitiphat.feedfold.util.FeedModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    List<FeedModel> bookmarkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String bookmark = sharedPreferences.getString("bookmark",null);

        if (bookmark != null) {
            bookmarkList = new Gson().fromJson(bookmark, bookmarkList.getClass());
        }

        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter();
        bookmarkAdapter.setBookmarkList(bookmarkList, BookmarkActivity.this);

        RecyclerView recyclerView = findViewById(R.id.rv_bookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarkActivity.this));
        recyclerView.setAdapter(bookmarkAdapter);
    }
}
