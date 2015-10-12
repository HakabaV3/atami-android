package com.atami.kikurage.atamikeyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.atami.kikurage.atamikeyboard.R;

public class NavBarView extends HorizontalScrollView {
    private Context mContext;
    private Paint mPaint;

    public NavBarView(Context context) {
        this(context, null, 0);
    }

    public NavBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        mPaint = new Paint();

        setupView();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorGrey300));
        canvas.drawRect(0, getHeight() - 3, getWidth(), getHeight(), mPaint);

        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        canvas.drawRect(0, 0, getWidth(), 3, mPaint);
    }

    private void setupView() {
        View.inflate(mContext, R.layout.view_navbar, this);
    }
}