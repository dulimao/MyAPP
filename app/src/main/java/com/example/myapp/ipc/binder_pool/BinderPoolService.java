package com.example.myapp.ipc.binder_pool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BinderPoolService extends Service {

    private static final String TAG = "BinderPoolService";
    private Binder mBinderPool =  new BinderPoolImpl();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBinder mBinderPool: " + mBinderPool);
        return mBinderPool;
    }
}
