package com.example.myapp.ipc.proxy_stub;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.myapp.R;



/**
 * Proxy-Stub模式1，模拟AIDL
 */
public class Main extends Activity {
    PlayerProxy mPlayerProxy;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerProxy = new PlayerProxy(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayerProxy = null;
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
        mPlayerProxy.paly();
    }

    public void stop(View view) {
        mPlayerProxy.stop();
    }
}
