package com.atami.kikurage.atamikeyboard.service;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.atami.kikurage.atamikeyboard.model.Stamp;
import com.atami.kikurage.atamikeyboard.model.StampAdapter;
import com.atami.kikurage.atamikeyboard.view.KeyboardBaseView;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class AtamiIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener, KeyboardBaseView.onKeyboardDelegate, StampAdapter.onStampActionDelegate {

    static public AtamiIME sIme;
    private KeyboardBaseView mView;
    private InputMethodManager mImm;
    private IBinder mToken;
    private StampAdapter mAdapter;

    @Override
    public View onCreateInputView() {
        Fabric.with(this, new Crashlytics());

        sIme = this;
        mView = new KeyboardBaseView(this);
        mView.setDelegate(this);

        mAdapter = new StampAdapter(this);
        mAdapter.setDelegate(this);
        mView.setAdapter(mAdapter);

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

    @Override
    public void onStampSelect(Stamp stamp) {
        Log.d("AtamiIME", stamp.url);
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(stamp.url, stamp.url.length());
    }

    @Override
    public void onTabItemSelect() {
        mAdapter.pGetImageSearch("");
    }
}