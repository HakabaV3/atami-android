package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridLayout;
import android.widget.ImageView;

import org.jdeferred.DoneCallback;

public class AtamiIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    static public AtamiIME instance;
    private View view;
    private GridLayout stampContainer;

    @Override
    public View onCreateInputView() {
        view = getLayoutInflater().inflate(R.layout.keyboard, null);

        stampContainer = (GridLayout) view.findViewById(R.id.stampContainer);
        instance = this;
        return view;
    }

    public boolean switchToLastInputMethod() {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        final IBinder token = this.getWindow().getWindow().getAttributes().token;

        return imm.switchToLastInputMethod(token);
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return this.switchToLastInputMethod();
        }

        return false;
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public void clearStamps() {

    }

    public void appendImageWithURL(final String url) {

        final ImageView image = new ImageView(this.getApplicationContext());
        try {
            stampContainer.addView(image);
        } catch (Throwable ex) {
            Log.d("Err", ex.toString());
        }

        API.pGetBitmap(url)
                .then(new DoneCallback<Bitmap>() {
                    @Override
                    public void onDone(Bitmap bitmap) {
                        image.setImageBitmap(bitmap);
                    }
                });
    }
}