package com.atami.kikurage.atamikeyboard;

import android.util.Log;

import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.android.AndroidDoneCallback;
import org.jdeferred.android.AndroidExecutionScope;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.WeakHashMap;

/**
 * @author Kikurage
 */
public class StampManager {

    public WeakHashMap<String, Stamp> stampMap;
    static private StampManager instance;

    static StampManager getDefaultManager() {
        if (instance == null) {
            instance = new StampManager();
        }

        return instance;
    }

    public StampManager() {
        stampMap = new WeakHashMap<String, Stamp>();
    }

    public Collection<Stamp> getAllStamps() {
        return stampMap.values();
    }


    public Promise<JSONArray, Throwable, Void> pGetImageSearch(String keyword) {
        return API.pGetJSONArray("http://atami.kikurage.xyz/api/v1/image/search?q=")
                .then(new AndroidDoneCallback<JSONArray>() {
                    @Override
                    public AndroidExecutionScope getExecutionScope() {
                        return AndroidExecutionScope.UI;
                    }

                    @Override
                    public void onDone(JSONArray stampDatum) {
                        try {
                            for (int i = 0; i < stampDatum.length(); i++) {
                                final Stamp stamp = new Stamp((JSONObject) stampDatum.get(i));
                                stampMap.put(stamp.id, stamp);

                                Log.d("StampManager", "Add stamp: id" + stamp.id);
                            }
                        } catch (JSONException ex) {
                            Log.d("StampManager", ex.toString());
                        }
                    }
                })
                .fail(new FailCallback<Throwable>() {
                    @Override
                    public void onFail(Throwable result) {
                        Log.d("StampManager", "pGetImageSearch failed: " + result.getMessage());
                    }
                });

    }
}
