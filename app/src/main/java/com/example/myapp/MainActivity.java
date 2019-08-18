package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapp.imageloader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //串行执行
//        new MyAsyncTask("Thread-1").execute("");
//        new MyAsyncTask("Thread-2").execute("");
//        new MyAsyncTask("Thread-3").execute("");
//        new MyAsyncTask("Thread-4").execute("");
//        new MyAsyncTask("Thread-5").execute("");
        //并行执行
//        new MyAsyncTask("Thread-1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        new MyAsyncTask("Thread-2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        new MyAsyncTask("Thread-3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        new MyAsyncTask("Thread-4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        new MyAsyncTask("Thread-5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");

        ImageView imageView = findViewById(R.id.imageview);
        ImageLoader.build(this).bindBitmap("https://www.baidu.com/img/bd_logo1.png",imageView,100,100);

    }
    class MyAsyncTask extends AsyncTask<String,Integer,String> {

        private String mName;

        public MyAsyncTask(String name) {
            mName = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d("MainActivity","result: " + s + " time: " + format.format(new Date()));
        }
    }

}
