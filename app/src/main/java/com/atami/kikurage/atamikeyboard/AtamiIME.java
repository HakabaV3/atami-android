package com.atami.kikurage.atamikeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;

public class AtamiIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener, StampActionDelegate {

    static public AtamiIME sIme;
    private View mView;
    private GridView mGirdView;
    private InputMethodManager mImm;
    private IBinder mToken;
    private StampAdapter mAdapter;

    @Override
    public View onCreateInputView() {
        sIme = this;
        mView = getLayoutInflater().inflate(R.layout.keyboard, null);
        mGirdView = (GridView) mView.findViewById(R.id.stampContainer);
        mAdapter = new StampAdapter(this);
        mAdapter.setDelegate(this);
        mGirdView.setAdapter(mAdapter);
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mToken = this.getWindow().getWindow().getAttributes().token;

        return mView;
    }

    public boolean switchToLastInputMethod() {
        return mImm.switchToLastInputMethod(mToken);
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

    public void update() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(Stamp stamp) {
        Log.d("AtamiIME", stamp.url);
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(stamp.url, stamp.url.length());
    }
}