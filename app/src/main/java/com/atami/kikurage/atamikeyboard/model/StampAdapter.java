package com.atami.kikurage.atamikeyboard.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.atami.kikurage.atamikeyboard.R;
import com.atami.kikurage.atamikeyboard.util.Http;
import com.atami.kikurage.atamikeyboard.view.StampView;

import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.android.AndroidDoneCallback;
import org.jdeferred.android.AndroidExecutionScope;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Kikurage
 */
public class StampAdapter extends BaseAdapter {
    public interface onStampActionDelegate {
        void onStampSelect(Stamp stamp);
    }

    private onStampActionDelegate mDelegate;
    private ArrayList<Stamp> mStamps;
    private LayoutInflater mInflater;

    private class ViewHolder {
        StampView image;
        Stamp stamp;
    }

    public StampAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStamps = new ArrayList<>();
    }

    public Promise<JSONArray, Throwable, Void> pGetImageSearch(String keyword) {

        mStamps.clear();
        notifyDataSetChanged();

        return Http.pGetJSONArray("http://atami.kikurage.xyz/api/v1/image/search?q=")
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
                                mStamps.add(stamp);

                                Log.d("StampManager", "Add stamp: id" + stamp.id);
                            }
                            notifyDataSetInvalidated();
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

    @Override
    public int getCount() {
        return mStamps.size();
    }

    @Override
    public Stamp getItem(int position) {
        return mStamps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("StampAdapter", "getView:" + position);

        StampView image;
        final ViewHolder holder;
        Stamp stamp = this.getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_stamp, parent, false);
            convertView.setMinimumHeight((int) (parent.getHeight() / 2.2));
            convertView.setOnClickListener(new StampClickListener());

            image = (StampView) convertView.findViewById(R.id.stampImage);
            convertView.setLayoutParams(getStampLayoutParams(parent));
            holder = new ViewHolder();
            holder.image = image;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setImageResource(R.mipmap.ic_placeholder);
        holder.stamp = stamp;

        Http.pGetBitmap(stamp.url)
                .then(new AndroidDoneCallback<Bitmap>() {
                    @Override
                    public AndroidExecutionScope getExecutionScope() {
                        return AndroidExecutionScope.UI;
                    }

                    @Override
                    public void onDone(Bitmap bitmap) {
                        holder.image.setImageBitmap(bitmap);
                    }
                });

        return convertView;
    }

    private AbsListView.LayoutParams getStampLayoutParams(View base) {
        return new AbsListView.LayoutParams(
                base.getWidth() / 3 - 8,
                base.getHeight() / 3 - 8
        );
    }

    public void setDelegate(onStampActionDelegate delegate) {
        mDelegate = delegate;
    }

    private class StampClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mDelegate == null) return;
            mDelegate.onStampSelect(((ViewHolder) v.getTag()).stamp);
        }
    }
}
