package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Collection;

/**
 * @author Kikurage
 */
public class StampAdapter extends BaseAdapter {

    private StampActionDelegate mDelegate;

    private class ViewHolder {
        FetchImageView image;
        Stamp stamp;
    }

    private class StampClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mDelegate == null) return;
            mDelegate.onClickListener(((ViewHolder) v.getTag()).stamp);
        }
    }

    private LayoutInflater inflater;

    public StampAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private Collection<Stamp> getStamps() {
        return StampManager.getDefaultManager().getAllStamps();
    }

    @Override
    public int getCount() {
        return getStamps().size();
    }

    @Override
    public Object getItem(int position) {
        return getStamps().toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("StampAdapter", "getView:" + position);

        FetchImageView image;
        ViewHolder holder;
        final Stamp stamp = (Stamp) this.getItem(position);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.keyboard_stamp, parent, false);
            convertView.setMinimumHeight((int) (parent.getHeight() / 2.2));
            convertView.setOnClickListener(new StampClickListener());

            image = (FetchImageView) convertView.findViewById(R.id.stampImage);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);

            holder = new ViewHolder();
            holder.image = image;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setUrl(stamp.proxiedUrl);
        holder.stamp = stamp;

        return convertView;
    }

    public void setDelegate(StampActionDelegate delegate) {
        mDelegate = delegate;
    }

    public StampActionDelegate getDelegate() {
        return mDelegate;
    }
}
