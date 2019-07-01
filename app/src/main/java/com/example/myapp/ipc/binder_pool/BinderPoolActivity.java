package com.example.myapp.ipc.binder_pool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.myapp.ISecurityCenter;
import com.example.myapp.R;

public class BinderPoolActivity extends Activity {
    private static final String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    public void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPoolImpl.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityBinder);
        String msg = "hello world 安卓";
        Log.d(TAG,"content: " + msg);
        try {
            String password = securityCenter.encrypt(msg);
            Log.d(TAG,"encypty: " + password);
            Log.d(TAG,"decypty: " + securityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }
}
