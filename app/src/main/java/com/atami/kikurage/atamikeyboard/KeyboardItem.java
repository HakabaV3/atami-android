package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class KeyboardItem extends FrameLayout {
    Button button;
    final String XML_NS_ANDROID = "http://schemas.android.com/apk/res/android";

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
        final View view = View.inflate(context, R.layout.keyboard_item, this);

        button = (Button) view.findViewById(R.id.keywordItemButton);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AtamiIME.instance.switchToLastInputMethod();
//                API.pGetJSONArray("http://atami.kikurage.xyz/image/search?q=%E3%82%88%E3%81%A4%E3%81%B0")
//                        .then(new DoneCallback<JSONArray>() {
//                            @Override
//                            public void onDone(JSONArray stamps) {
//                                AtamiIME ime = AtamiIME.instance;
//                                try {
//                                    ime.clearStamps();
//                                    for (int i = 0; i < stamps.length(); i++) {
//                                        JSONObject stamp = (JSONObject) stamps.get(i);
//                                        String url = stamp.getString("proxiedUrl");
//                                        ime.appendImageWithURL(url);
//                                    }
//
//                                } catch (JSONException ex) {
//                                    Log.d("url", "exception");
//                                }
//                            }
//                        });
            }
        });
    }

    private void setupAttribute(AttributeSet attrs) {
        if (attrs == null) return;

        CharSequence text = attrs.getAttributeValue(XML_NS_ANDROID, "text");
        if (text != null) this.setText(text);
    }

    public void setText(CharSequence text) {
        button.setText(text);
    }

    public CharSequence getText() {
        return button.getText();
    }
}