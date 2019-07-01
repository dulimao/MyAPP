package com.example.myapp.ipc.call_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service implements IS{

    private IBinder mBinder = new MyBinder();



    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {

        //返回当前服务的对象
        public IS getService() {
            return MyService.this;
        }
    }


    @Override
    public void f1() {
        Log.d("MyService","func1()");
    }

    @Override
    public void f2() {
        Log.d("MyService","func1()");
    }
}
