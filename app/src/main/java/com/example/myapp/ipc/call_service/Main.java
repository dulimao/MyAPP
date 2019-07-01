package com.example.myapp.ipc.call_service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.ipc.proxy_stub.MP3RemoteService;

/**
 * 短程通信，同一个进程，Activity调用Service中的方法
 */
public class Main extends Activity {
   private IS mIS;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder mBinder = (MyService.MyBinder) service;
            mIS = mBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定服务
        Intent intent = new Intent(this, MP3RemoteService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);

    }


    public void play(View view) {
        //此处通过接口调用Service的方法
        mIS.f1();
    }

    public void stop(View view) {
        mIS.f2();
    }
}
