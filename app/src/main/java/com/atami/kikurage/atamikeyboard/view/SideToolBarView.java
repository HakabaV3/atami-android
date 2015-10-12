package com.atami.kikurage.atamikeyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.atami.kikurage.atamikeyboard.R;

public class SideToolBarView extends LinearLayout {
    private Context mContext;
    private Paint mPaint;

    public SideToolBarView(Context context) {
        this(context, null, 0);
    }

    public SideToolBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideToolBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mPaint = new Paint();
        setWillNotDraw(false);

        setupView();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorGrey300));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 40, 3, getHeight() - 40, mPaint);
    }

    private void setupView() {
        View.inflate(mContext, R.layout.view_side_toolbar, this);
    }
}