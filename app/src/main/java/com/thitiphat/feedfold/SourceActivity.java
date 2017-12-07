package com.thitiphat.feedfold;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thitiphat.feedfold.adapter.SourceAdapter;
import com.thitiphat.feedfold.model.SourceModel;

public class SourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Source");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        SourceModel sourceModel = new SourceModel();

        SourceAdapter sourceAdapter = new SourceAdapter();
        sourceAdapter.setSourceModel(sourceModel);
        sourceAdapter.setCategory(category);
        sourceAdapter.setContext(this);

        RecyclerView recyclerView = findViewById(R.id.rvSrcList);
        recyclerView.setLayoutManager(new LinearLayoutManager(SourceActivity.this));
        recyclerView.setAdapter(sourceAdapter);
    }
}
