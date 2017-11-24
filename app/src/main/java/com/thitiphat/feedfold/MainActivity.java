package com.thitiphat.feedfold;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thitiphat.feedfold.Interface.RecyclerViewClickListener;
import com.thitiphat.feedfold.adapter.FeedAdapter;
import com.thitiphat.feedfold.model.FeedModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    List<FeedModel> feedList = new ArrayList<>();
    String urlLink, result, title, link, description, creator, ns = null;
    String date;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Context context;

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

        urlLink = "https://www.blognone.com/atom.xml";
        String urlLink2 = "http://feed.androidauthority.com";
        String urlLink3 = "https://www.engadget.com/rss.xml";
        String urlLink4 = "https://lifehacker.com/rss";
        String urlll = "https://nuuneoi.com/blog/rssfeed.php";

        LoadXml loadXml = new LoadXml();
        loadXml.execute(urlLink, urlLink2, urlLink3, urlLink4, urlll);
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public int reyclerViewListClicked(View v, int position) {
        FeedAdapter feedAdapter = new FeedAdapter(getApplicationContext(), this);
//        Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
//        intent.putExtra("ITEM", feedList.get(position).getTitle());
//        startActivity(intent);
        return position;
    }

    public class LoadXml extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            SortItem sortItem = new SortItem();
            try {

                for (String string : strings) {
                    URL url = new URL(string);
                    InputStream inputStream = url.openConnection().getInputStream();

//                XmlPullParser xmlPullParser = Xml.newPullParser();
//                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//                xmlPullParser.setInput(inputStream, null);
//                xmlPullParser.nextTag();
//                feedList = readFeedGoogle(xmlPullParser);

                    feedList = readFeed(inputStream);

                }
                feedList = sortItem.sortList(feedList);
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
            FeedAdapter feedAdapter = new FeedAdapter();
            feedAdapter.setFeedList(feedList, MainActivity.this);

            RecyclerView recyclerView = findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(feedAdapter);
        }
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

    public List<FeedModel> readFeedGoogle(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        List<FeedModel> entries = new ArrayList();

//        XmlPullParser xmlPullParser = Xml.newPullParser();
//        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//        xmlPullParser.setInput(inputStream, "UTF-8");
//        xmlPullParser.nextTag();

        xmlPullParser.require(XmlPullParser.START_TAG, ns, "channel");
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            if (name.equals("item")) {
                entries.add(readEntry(xmlPullParser));
            } else {
                skip(xmlPullParser);
            }
        }
        return entries;
    }

    private FeedModel readEntry(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String link = null;
        String description = null;

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            String name = xmlPullParser.getName();
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
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
            } else {
                skip(xmlPullParser);
            }
        }
        return new FeedModel(title, link, description);
    }

    private String readTitle(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readDesc(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, ns, "description");
        String title = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "description");
        return title;
    }

    public String readText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String result = "";
        if (xmlPullParser.next() == XmlPullParser.TEXT) {
            result = xmlPullParser.getText();
            xmlPullParser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {

        if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (xmlPullParser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
