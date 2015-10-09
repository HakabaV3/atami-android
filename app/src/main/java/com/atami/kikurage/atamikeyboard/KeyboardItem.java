package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.jdeferred.FailCallback;
import org.jdeferred.android.AndroidDoneCallback;
import org.jdeferred.android.AndroidExecutionScope;
import org.json.JSONArray;

public class KeyboardItem extends FrameLayout {
    final String XML_NS_ANDROID = "http://schemas.android.com/apk/res/android";

    private OnClickListener mClickListener;
    private Button mButton;

    public KeyboardItem(Context context) {
        super(context);
        setupView(context);
    }

    public KeyboardItem(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setupView(context);
        setupAttribute(attrs);
    }

    public KeyboardItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
        setupAttribute(attrs);
    }

    private void setupView(Context context) {
        View.inflate(context, R.layout.keyboard_item, this);

        mButton = (Button) this.findViewById(R.id.keywordItemButton);
        mButton.setOnClickListener(getOnClickListener());
    }

    private OnClickListener getOnClickListener() {
        if (mClickListener == null) {
            mClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    StampManager.getDefaultManager()
                            .pGetImageSearch("")
                            .then(new AndroidDoneCallback<JSONArray>() {
                                @Override
                                public void onDone(JSONArray result) {
                                    AtamiIME.sIme.update();
                                }

                                @Override
                                public AndroidExecutionScope getExecutionScope() {
                                    return AndroidExecutionScope.UI;
                                }
                            })
                            .fail(new FailCallback<Throwable>() {
                                @Override
                                public void onFail(Throwable result) {
                                    Log.d("KeyboardItem", "pGet failed: " + result.getMessage());
                                }
                            });
                }
            };
        }

        return mClickListener;
    }

    private void setupAttribute(AttributeSet attrs) {
        if (attrs == null) return;

        CharSequence text = attrs.getAttributeValue(XML_NS_ANDROID, "text");
        if (text != null) this.setText(text);
    }

    public void setText(CharSequence text) {
        mButton.setText(text);
    }

    public CharSequence getText() {
        return mButton.getText();
    }
}