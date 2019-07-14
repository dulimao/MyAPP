package com.example.myapp.plugin;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapp.R;

import java.io.File;


//资料地址：https://blog.csdn.net/singwhatiwanna/article/details/22597587
public class PluginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
    }


    public void loadApk(View view) {
        Intent intent = new Intent(this,ProxyActivity.class);
//        intent.putExtra(ProxyActivity.EXTRA_CLASS,"com.example.plugin.TestActivity");
        startActivity(intent);
    }
}
