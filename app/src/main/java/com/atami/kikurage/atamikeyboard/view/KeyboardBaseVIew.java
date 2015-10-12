package com.atami.kikurage.atamikeyboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.atami.kikurage.atamikeyboard.R;

/**
 * @author Kikurage
 */
public class KeyboardBaseView extends LinearLayout {

    public interface onKeyboardDelegate {
        void onTabItemSelect();
    }

    private Context mContext;
    private GridView mGridView;
    private LinearLayout mTabItemContainer;
    private onKeyboardDelegate mDelegate;

    static private String[] mTabItems = {"item1", "item2", "item3"};

    public KeyboardBaseView(Context context) {
        this(context, null, 0);
    }

    public KeyboardBaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        setupView();
    }

    private void setupView() {
        View.inflate(mContext, R.layout.view_keyboard_base, this);

        mGridView = (GridView) findViewById(R.id.stampGrid);
        mTabItemContainer = (LinearLayout) findViewById(R.id.tabContainer);
        for (String tabItem : mTabItems) {
            TabItemView item = new TabItemView(mContext);
            item.setText(tabItem);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("AtamiIME", "onclick");

                    if (mDelegate == null) return;
                    mDelegate.onTabItemSelect();
                }
            });
            mTabItemContainer.addView(item);
        }
    }

    public void setDelegate(onKeyboardDelegate delegate) {
        mDelegate = delegate;
    }

    public void setAdapter(BaseAdapter adapter) {
        mGridView.setAdapter(adapter);
    }
}