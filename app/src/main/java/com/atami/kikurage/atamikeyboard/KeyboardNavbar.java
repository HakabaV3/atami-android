package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class KeyboardNavbar extends FrameLayout {
    final String XML_NS_ANDROID = "http://schemas.android.com/apk/res/android";
    private View mView;

    public KeyboardNavbar(Context context) {
        super(context);
        setupView(context);
    }

    public KeyboardNavbar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setupView(context);
        setupAttribute(attrs);
    }

    public KeyboardNavbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
        setupAttribute(attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1.5f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    private void setupView(Context context) {
        mView = View.inflate(context, R.layout.keyboard_navbar, this);
    }

    private void setupAttribute(AttributeSet attrs) {
        if (attrs == null) return;

        //@TODO update view depend on attrs
    }
}