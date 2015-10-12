package com.atami.kikurage.atamikeyboard.model;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kikurage
 */
public class Stamp {
    public String url;
    public String proxiedUrl;
    public String id;
    public Bitmap bitmap;

    public Stamp(JSONObject stampData) throws JSONException {
        url = stampData.getString("url");
        proxiedUrl = stampData.getString("proxiedUrl");
        id = stampData.getString("_id");
        bitmap = null;

//        @TODO tags
    }
}
