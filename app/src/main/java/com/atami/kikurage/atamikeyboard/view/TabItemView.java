package com.atami.kikurage.atamikeyboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.atami.kikurage.atamikeyboard.R;
import com.atami.kikurage.atamikeyboard.model.StampAdapter;

public class TabItemView extends FrameLayout {
    final String XML_NS_ANDROID = "http://schemas.android.com/apk/res/android";

    private Button mButton;
    private StampAdapter mAdapter;
    private Context mContext;

    public TabItemView(Context context) {
        this(context, null, 0);
    }

    public TabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
        setupAttribute(attrs);
    }

    private void setupView(Context context) {
        mContext = context;
        View.inflate(mContext, R.layout.view_tab_item, this);

        mButton = (Button) this.findViewById(R.id.button);
    }

    private void setupAttribute(AttributeSet attrs) {
        if (attrs == null) return;

        CharSequence text = attrs.getAttributeValue(XML_NS_ANDROID, "text");
        if (text != null) this.setText(text);
    }

    public void setOnClickListener(OnClickListener listener) {
        mButton.setOnClickListener(listener);
    }

    public void setText(CharSequence text) {
        mButton.setText(text);
    }

    public CharSequence getText() {
        return mButton.getText();
    }

    public void setAdapter(StampAdapter adapter) {
        mAdapter = adapter;
    }

    public StampAdapter getAdapter() {
        return mAdapter;
    }
}