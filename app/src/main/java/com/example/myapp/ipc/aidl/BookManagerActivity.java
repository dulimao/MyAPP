package com.example.myapp.ipc.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.myapp.Book;
import com.example.myapp.IBookArrivedListener;
import com.example.myapp.IBookMananger;
import com.example.myapp.R;

import java.util.List;

public class BookManagerActivity extends Activity {

    private static final String TAG = "BookManagerActivity";
    IBookMananger mBookManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookMananger.Stub.asInterface(service);
            try {
                List list = mBookManager.getBookList();
                Log.d(TAG,list.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_layout);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }


    //客户端调服务端,调用服务端后客户端当前线程会被挂起
    public void test(View view) {
        try {
            Log.d(TAG,"add book start");
            mBookManager.addBook(new Book(1,"毛泽东全传"));
            Log.d(TAG,"add book end");

            List list = mBookManager.getBookList();
            Log.d(TAG,list.toString());

            mBookManager.registerListener(listener);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IBookArrivedListener listener = new IBookArrivedListener.Stub() {
        @Override
        public void bookArrived(Book book) throws RemoteException {
            //此处代码运行在客户端的Binder线程池中，所以不能访问UI线程，需要使用handler
            Log.d(TAG,"new book arriveing " + Thread.currentThread().getName());

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);

    }
}
