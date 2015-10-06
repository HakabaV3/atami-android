package com.atami.kikurage.atamikeyboard;

/**
 * @author Kikurage
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.DonePipe;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public class API {

    static private DonePipe<HttpResponse, JSONObject, Throwable, Void> jsonFilter;
    static private DonePipe<HttpResponse, JSONArray, Throwable, Void> jsonArrayFilter;
    static private DonePipe<HttpResponse, Bitmap, Throwable, Void> bitmapParser;
    static private DeferredManager ddm;

    static Promise<HttpResponse, Throwable, Void> pGet(final String url) {
        return getDDM()
                .when(new Callable<HttpResponse>() {
                    @Override
                    public HttpResponse call() throws Exception {
                        HttpClient client = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(url);
                        HttpResponse response;
                        response = client.execute(httpGet);

                        return response;
                    }
                });
    }

    static Promise<JSONObject, Throwable, Void> pGetJSON(String url) {
        return API.pGet(url)
                .then(API.getJSONParser());
    }

    static Promise<JSONArray, Throwable, Void> pGetJSONArray(String url) {
        return API.pGet(url)
                .then(API.getJSONArrayParser());
    }

    static Promise<Bitmap, Throwable, Void> pGetBitmap(String url) {
        return API.pGet(url)
                .then(API.getBitmapParser());
    }

    static private DeferredManager getDDM() {
        if (ddm == null) {
            ddm = new DefaultDeferredManager();
        }
        return ddm;
    }

    static private DonePipe<HttpResponse, JSONObject, Throwable, Void> getJSONParser() {
        if (jsonFilter == null) {
            jsonFilter = new DonePipe<HttpResponse, JSONObject, Throwable, Void>() {
                @Override
                public Deferred<JSONObject, Throwable, Void> pipeDone(HttpResponse response) {
                    String responseText;
                    JSONObject json;
                    try {
                        responseText = EntityUtils.toString(response.getEntity(), "UTF-8");
                        json = new JSONObject(responseText);
                    } catch (Throwable ex) {
                        return new DeferredObject<JSONObject, Throwable, Void>().reject(ex);
                    }

                    return new DeferredObject<JSONObject, Throwable, Void>().resolve(json);
                }
            };
        }

        return jsonFilter;
    }

    static private DonePipe<HttpResponse, JSONArray, Throwable, Void> getJSONArrayParser() {
        if (jsonArrayFilter == null) {
            jsonArrayFilter = new DonePipe<HttpResponse, JSONArray, Throwable, Void>() {
                @Override
                public Deferred<JSONArray, Throwable, Void> pipeDone(HttpResponse response) {
                    String responseText;
                    JSONArray json;

                    try {
                        responseText = EntityUtils.toString(response.getEntity(), "UTF-8");
                        json = new JSONArray(responseText);
                    } catch (Throwable ex) {
                        return new DeferredObject<JSONArray, Throwable, Void>().reject(ex);
                    }

                    return new DeferredObject<JSONArray, Throwable, Void>().resolve(json);
                }
            };
        }

        return jsonArrayFilter;
    }

    static private DonePipe<HttpResponse, Bitmap, Throwable, Void> getBitmapParser() {
        if (bitmapParser == null) {
            bitmapParser = new DonePipe<HttpResponse, Bitmap, Throwable, Void>() {
                @Override
                public Promise<Bitmap, Throwable, Void> pipeDone(HttpResponse response) {
                    InputStream is;
                    try {
                        is = response.getEntity().getContent();
                    } catch (IOException ex) {
                        return new DeferredObject<Bitmap, Throwable, Void>().reject(ex);
                    }
                    Bitmap bit = BitmapFactory.decodeStream(is);

                    return new DeferredObject<Bitmap, Throwable, Void>().resolve(bit);
                }
            };
        }

        return bitmapParser;
    }
}
