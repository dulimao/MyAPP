package com.example.myapp.ipc.proxy_stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MP3RemoteService extends Service {

    private IBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MP3Binder(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
