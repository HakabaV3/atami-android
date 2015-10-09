package com.atami.kikurage.atamikeyboard;

/**
 * @author Kikurage
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.DonePipe;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.android.AndroidDeferredManager;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.Callable;

public class API {

    static private DonePipe<String, JSONObject, Throwable, Void> jsonFilter;
    static private DonePipe<String, JSONArray, Throwable, Void> jsonArrayFilter;
    static private DeferredManager ddm;

    static Promise<String, Throwable, Void> pGet(final String url) {
        Log.d("API", "GET: " + url);

        return getDDM()
                .when(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpClient client = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(url);
                        HttpResponse response;
                        response = client.execute(httpGet);

                        Log.d("API", "pGet success: " + url);

                        return EntityUtils.toString(response.getEntity());
                    }
                })
                .fail(new FailCallback<Throwable>() {
                    @Override
                    public void onFail(Throwable result) {
                        Log.d("API", "pGet failed: " + result.getMessage());
                    }
                });
    }

    static Promise<JSONObject, Throwable, Void> pGetJSON(String url) {
        return API.pGet(url)
                .then(API.getJSONParser())
                .fail(new FailCallback<Throwable>() {
                    @Override
                    public void onFail(Throwable result) {
                        Log.d("API", "pGetJSON failed: " + result.getMessage());
                    }
                });

    }

    static Promise<JSONArray, Throwable, Void> pGetJSONArray(String url) {
        return API.pGet(url)
                .then(API.getJSONArrayParser())
                .fail(new FailCallback<Throwable>() {
                    @Override
                    public void onFail(Throwable result) {
                        Log.d("API", "pGetJSONArray failed: " + result.getMessage());
                    }
                });


    }

    static Promise<Bitmap, Throwable, Void> pGetBitmap(final String url) {
        Log.d("API", "GET: " + url);

        return getDDM()
                .when(new Callable<Bitmap>() {
                    @Override
                    public Bitmap call() throws Exception {
                        HttpClient client = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(url);
                        HttpResponse response;
                        response = client.execute(httpGet);
                        InputStream is;
                        is = response.getEntity().getContent();
                        return BitmapFactory.decodeStream(is);
                    }
                })
                .fail(new FailCallback<Throwable>() {
                    @Override
                    public void onFail(Throwable result) {
                        Log.d("API", "pGetBitmap failed: " + result.getMessage());
                    }
                });

    }

    static private DeferredManager getDDM() {
        if (ddm == null) {
            ddm = new AndroidDeferredManager();
        }
        return ddm;
    }

    static private DonePipe<String, JSONObject, Throwable, Void> getJSONParser() {
        if (jsonFilter == null) {
            jsonFilter = new DonePipe<String, JSONObject, Throwable, Void>() {
                @Override
                public Deferred<JSONObject, Throwable, Void> pipeDone(String response) {
                    JSONObject json;
                    try {
                        json = new JSONObject(response);
                    } catch (Throwable ex) {
                        return new DeferredObject<JSONObject, Throwable, Void>().reject(ex);
                    }

                    return new DeferredObject<JSONObject, Throwable, Void>().resolve(json);
                }
            };
        }

        return jsonFilter;
    }

    static private DonePipe<String, JSONArray, Throwable, Void> getJSONArrayParser() {
        if (jsonArrayFilter == null) {
            jsonArrayFilter = new DonePipe<String, JSONArray, Throwable, Void>() {
                @Override
                public Deferred<JSONArray, Throwable, Void> pipeDone(String response) {
                    JSONArray json;

                    try {
                        json = new JSONArray(response);
                    } catch (Throwable ex) {
                        return new DeferredObject<JSONArray, Throwable, Void>().reject(ex);
                    }

                    return new DeferredObject<JSONArray, Throwable, Void>().resolve(json);
                }
            };
        }

        return jsonArrayFilter;
    }
}
