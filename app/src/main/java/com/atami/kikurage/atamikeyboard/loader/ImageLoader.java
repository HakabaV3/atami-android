package com.atami.kikurage.atamikeyboard.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kikurage
 */
public class ImageLoader extends AsyncTaskLoader<Bitmap> {

    private String mUrl = "";

    public ImageLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public Bitmap loadInBackground() {
        Log.d("ImageLoader", "Loading");

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(mUrl);
        HttpResponse response;
        try {
            response = client.execute(httpGet);
        } catch (IOException e) {
            return null;
        }

        InputStream is;
        try {
            is = response.getEntity().getContent();
        } catch (IOException e) {
            return null;
        }

        Log.d("ImageLoader", "Loaded");
        return BitmapFactory.decodeStream(is);
    }
}
