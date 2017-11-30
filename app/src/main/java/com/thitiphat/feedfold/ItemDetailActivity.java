package com.thitiphat.feedfold;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thitiphat.feedfold.model.FeedModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener, Html.ImageGetter {

    TextView tvDesc;
    String TAG = "Test Img Getter";
    ActionBarDrawerToggle actionBarDrawerToggle;
    Intent intent;

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

        Button visit = findViewById(R.id.visit_btn);
        visit.setOnClickListener(this);

        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailTitle.setText(intent.getStringExtra("title"), null);
        tvDetailTitle.setOnClickListener(this);

        tvDesc = findViewById(R.id.tvDetail);

        String source = intent.getStringExtra("description");
        Spanned spanned = Html.fromHtml(source, this, null);
        tvDesc.setText(spanned);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_menu, menu);
        return true;
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
        if (item.getItemId() == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, (getIntent().getStringExtra("link")));
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        if (item.getItemId() == R.id.action_bookmark) {

//            SharedPreferences sharedPreferences = getSharedPreferences("list", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            List<FeedModel> bookmarkList = new ArrayList<>();
//            final String bookmark = sharedPreferences.getString("bookmark", null);
//            bookmarkList = new Gson().fromJson(bookmark, List.class);
//
//            FeedModel feedModel = new FeedModel();
//            feedModel.setTitle(intent.getStringExtra("title"));
//            feedModel.setLink(intent.getStringExtra("description"));
//            bookmarkList.add(feedModel);
//
//            LinearLayout linear = findViewById(R.id.linear);
//            Snackbar.make(linear, intent.getStringExtra("title"), Snackbar.LENGTH_LONG)
//                    .setAction("Undo", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    }).show();
//
//
//            String bm = new Gson().toJson(bookmarkList);
//            editor.putString("bookmark", bm);
//            editor.apply();
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

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.ic_add_white_24dp);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tvDesc.getText();
                tvDesc.setText(t);
            }
        }
    }
}
