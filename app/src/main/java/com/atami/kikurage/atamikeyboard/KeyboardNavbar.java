package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class KeyboardNavbar extends HorizontalScrollView {
    final String XML_NS_ANDROID = "http://schemas.android.com/apk/res/android";
    private Context mContext;

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

        paint.setColor(ContextCompat.getColor(mContext, R.color.colorGrey50));
        paint.setStrokeWidth(1.5f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, getHeight() - 1, getWidth(), getHeight(), paint);
    }

    private void setupView(Context context) {
        mContext = context;
        View.inflate(context, R.layout.keyboard_navbar, this);
    }

    private void setupAttribute(AttributeSet attrs) {
        if (attrs == null) return;

        //@TODO update view depend on attrs
    }
}