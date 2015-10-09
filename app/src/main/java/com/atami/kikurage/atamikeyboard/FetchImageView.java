package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

/**
 * @author Kikurage
 */
public class FetchImageView extends ImageView {
    private String mUrl;

    public FetchImageView(Context context) {
        this(context, null, 0);
    }

    public FetchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FetchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUrl(String url) {
        mUrl = url;
        setImageBitmap(null);

        API.pGetBitmap(url)
                .then(new DoneCallback<Bitmap>() {
                    @Override
                    public void onDone(Bitmap bitmap) {
                        setImageBitmap(bitmap);
                    }
                })
                .fail(new FailCallback<Throwable>() {
                    @Override
                    public void onFail(Throwable result) {
                        Log.d("FetchImageView", "failed: " + mUrl);
                    }
                });
    }

    public String getUrl() {
        return mUrl;
    }

}
