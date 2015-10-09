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

    private class ViewHolder {
        FetchImageView image;
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
        Stamp stamp = (Stamp) this.getItem(position);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.keyboard_stamp, parent, false);
            convertView.setMinimumHeight((int) (parent.getHeight() / 2.2));

            image = (FetchImageView) convertView.findViewById(R.id.stampImage);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);

            holder = new ViewHolder();
            holder.image = image;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setUrl(stamp.proxiedUrl);

        return convertView;
    }
}
