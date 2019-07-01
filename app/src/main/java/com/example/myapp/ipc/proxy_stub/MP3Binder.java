package com.example.myapp.ipc.proxy_stub;

import android.content.Context;
import android.media.MediaPlayer;

public class MP3Binder extends PlayerStub {

    private MediaPlayer mPlayer;
    private Context mContext;

    public MP3Binder(Context context) {
        this.mContext = context;
    }

    @Override
    public void paly() {

        if (mPlayer != null) {
            return;
        }
        mPlayer = MediaPlayer.create(mContext,null);
        mPlayer.start();
    }

    @Override
    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public String getStatus() {
        return null;
    }
}
