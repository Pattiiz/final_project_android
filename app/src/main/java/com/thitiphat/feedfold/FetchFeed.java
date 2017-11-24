package com.thitiphat.feedfold;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by phatm on 11/16/2017.
 */

public class FetchFeed extends AsyncTask<Void, Void, Boolean> {

    private String urlLink;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

//        OkHttpClient client = new OkHttpClient();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlLink).client(client).build();

        try {
            URL url = new URL(urlLink);
            InputStream inputStream = url.openConnection().getInputStream();
            FeedExtacter feedExtacter = new FeedExtacter(inputStream);
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
        super.onPostExecute(aBoolean);
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public FetchFeed(String urlLink) {
        this.urlLink = urlLink;
    }
}
