package com.atami.kikurage.atamikeyboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Kikurage
 */
public class StampGridView extends GridView {

    public StampGridView(Context context) {
        this(context, null, 0);
    }

    public StampGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
