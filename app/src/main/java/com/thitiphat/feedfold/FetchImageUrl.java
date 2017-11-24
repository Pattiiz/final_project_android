package com.thitiphat.feedfold;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by phatm on 11/24/2017.
 */

public class FetchImageUrl extends AsyncTask<String, String, Boolean> {


    String imageUrl;
    Context context;
    protected Drawable image;

    public FetchImageUrl(Context context, String url)
    {
        this.imageUrl = url;
        image = null;
        this.context = context;
    }

    public Drawable GetImage()
    {
        return image;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... args) {
        try {
            InputStream input_stream = (InputStream) new URL(imageUrl).getContent();
            image = Drawable.createFromStream(input_stream, "src name");
            return true;

        }
        catch (Exception e)
        {
            image = null;
        }
        return false;
    }


    @Override
    protected void onPostExecute(Boolean result) {

    }}
