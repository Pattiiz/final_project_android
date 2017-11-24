package com.thitiphat.feedfold;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

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

        Intent intent = getIntent();

        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailTitle.setText(intent.getStringExtra("title"), null);
        tvDetailTitle.setOnClickListener(this);
        TextView tvDesc = findViewById(R.id.tvDetail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDesc.setText("\n" + Html.fromHtml(intent.getStringExtra("description").toString(), Html.FROM_HTML_MODE_COMPACT) + "\n");
        } else {
            tvDesc.setText("\n" + Html.fromHtml(intent.getStringExtra("description").toString()) + "\n");
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.showHome) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvDetailTitle) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabs = builder.build();
            customTabs.launchUrl(this, Uri.parse(getIntent().getStringExtra("link")));
        }
    }
}
