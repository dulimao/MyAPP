package com.example.myapp.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.example.myapp.Book;
import com.example.myapp.IBookArrivedListener;
import com.example.myapp.IBookMananger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BookMangerService";
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<IBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private AtomicBoolean mIsServiceDestroy = new AtomicBoolean(false);
    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"三国演义"));
        mBookList.add(new Book(2,"金瓶梅"));
        //每隔五分钟给调用客户端的方法，通知客户端，相当于观察者模式
        new Thread(new ServiceWorker()).start();
    }

    private Binder mBinder = new IBookMananger.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5000);
            return mBookList;
        }

        @Override
        public void addBook(final Book book) throws RemoteException {
            mBookList.add(book);
            Log.d(TAG,"add a book in service");


        }

        @Override
        public void registerListener(IBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG,"listener already exists");
            }
            Log.d(TAG,"registerListenerSize: " + mListenerList.size());
        }

        @Override
        public void unregisterListener(IBookArrivedListener listener) throws RemoteException {
            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
            }
            Log.d(TAG,"registerListenerSize: " + mListenerList.size());
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroy.set(true);
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestroy.get()) {
                try {
                    Thread.sleep(5000);
                    Book book = new Book(mBookList.size() + 1,"金瓶梅" + mBookList.size());
                    mBookList.add(book);
                    for (int i = 0; i < mListenerList.size(); i++) {
                        IBookArrivedListener listener = mListenerList.get(i);
                        listener.bookArrived(book);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
