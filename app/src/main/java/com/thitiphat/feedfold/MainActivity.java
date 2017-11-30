package com.thitiphat.feedfold;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thitiphat.feedfold.Interface.RecyclerViewClickListener;
import com.thitiphat.feedfold.adapter.FeedAdapter;
import com.thitiphat.feedfold.model.FeedModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener, NavigationView.OnNavigationItemSelectedListener {

    List<FeedModel> feedList = new ArrayList<>();
    String result, title, link, description, creator, ns = null;
    String date;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Context context;
    List<String> srcList = new ArrayList<>();
    FeedAdapter feedAdapter = new FeedAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String src = sharedPreferences.getString("json", null);
        if (src != null) {
            srcList = new Gson().fromJson(src, List.class);
        } else {
            return;
        }

        if (!checkInernetConnection()) {
            displayDialog();
        }

        LoadXml loadXml = new LoadXml();
        loadXml.execute(srcList);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        actionBarDrawerToggle.syncState();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_refresh) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String src = sharedPreferences.getString("json", null);
            if (src != null) {
                srcList = new Gson().fromJson(src, List.class);
            }
            LoadXml loadXml = new LoadXml();
            feedList.clear();
            feedAdapter.notifyDataSetChanged();
            loadXml.execute(srcList);
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int reyclerViewListClicked(View v, int position) {
        FeedAdapter feedAdapter = new FeedAdapter(getApplicationContext(), this);
        return position;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_timeline) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String src = sharedPreferences.getString("json", null);
            if (src != null) {
                srcList = new Gson().fromJson(src, List.class);
            }
            LoadXml loadXml = new LoadXml();
            feedList.clear();
            feedAdapter.notifyDataSetChanged();
            loadXml.execute(srcList);

            TextView userTwitter = findViewById(R.id.tv_userTW);
            userTwitter.setText(getIntent().getStringExtra("userTwitter"));

            drawerLayout.closeDrawers();
        }
        if (item.getItemId() == R.id.nav_bookmark) {
//            SharedPreferences sharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            String src = sharedPreferences.getString("bookmark", null);
//
//            feedList.clear();
//            feedAdapter.notifyDataSetChanged();
//            if (src != null) {
//                feedList = new Gson().fromJson(src, List.class);
//            }
//            setAdapter();

            TextView userTwitter = findViewById(R.id.tv_userTW);
            userTwitter.setText(getIntent().getStringExtra("userTwitter"));
            drawerLayout.closeDrawers();
        }
        return false;
    }

    public Boolean checkInernetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please connect to Internet")
                .setTitle("No Internet Connection");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class LoadXml extends AsyncTask<List<String>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<String>... srcList) {
            SortItem sortItem = new SortItem();
            try {
                for (String src : srcList[0]) {
                    URL url = new URL(src);
                    InputStream inputStream = url.openConnection().getInputStream();
                    feedList = readFeed(inputStream);
                }
                feedList = sortItem.sortList(feedList);
                sortItem.clearList();
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            setAdapter();
        }
    }

    public void setAdapter() {
        feedAdapter.setFeedList(feedList, MainActivity.this);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(feedAdapter);
    }

    public List<FeedModel> readFeed(InputStream inputStream) throws XmlPullParserException, IOException {

        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(inputStream, "UTF-8");
        xmlPullParser.nextTag();

        int evenType = xmlPullParser.getEventType();
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {

            String name = xmlPullParser.getName();
            if (name == null) {
                continue;
            }
            if (evenType != XmlPullParser.START_TAG) {
                continue;
            }
            if (xmlPullParser.next() == XmlPullParser.TEXT) {
                result = xmlPullParser.getText();
                xmlPullParser.nextTag();
            }
            if (name.equalsIgnoreCase("title")) {
                title = result;
            }
            if (name.equalsIgnoreCase("link")) {
                link = result;
            }
            if (name.equalsIgnoreCase("description")) {
                description = result;
            }
            if (name.equalsIgnoreCase("pubDate")) {
                date = result;
            }
            if (name.equalsIgnoreCase("dc:creator")) {
                creator = result;
            }
            if (title != null && link != null && description != null && date != null && creator != null) {
                FeedModel feedModel = new FeedModel();

                feedModel.setTitle(title);
                feedModel.setDescription(description);
                feedModel.setLink(link);
                feedModel.setPubDate(date);
                feedModel.setCreator(creator);
                feedList.add(feedModel);

                title = null;
                description = null;
                link = null;
                date = null;
                creator = null;
            }
        }
        inputStream.close();
        return feedList;
    }

}
