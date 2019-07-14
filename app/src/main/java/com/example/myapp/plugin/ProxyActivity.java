package com.example.myapp.plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.corelibrary.utils.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class ProxyActivity extends AppCompatActivity {
    private static final String TAG = "ProxyActivity";
    public static final String FROM = "extra.from";
    public static final int FROM_EXTERNAL = 0;
    public static final int FROM_INTERNAL = 1;

    public static final String EXTRA_DEX_PATH = "extra.dex.path";
    public static final String EXTRA_CLASS = "extra.class";
    public static final String FILE_NAME = "plugin-debug.apk";

    private String mClass;
    private String mDexPath;
    private String mCacheDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_proxy);
        mClass = getIntent().getStringExtra(EXTRA_CLASS);
//        mDexPath = getIntent().getStringExtra(EXTRA_DEX_PATH);
        mDexPath = Utils.copyFiles(this,FILE_NAME);
        mCacheDir = Utils.getCacheDir(this).getAbsolutePath();
        Log.d(TAG,"mClass = " + mClass + " mDexPath: " + mDexPath + " mCacheDir: " + mCacheDir);

        if (mClass == null) {
            launchTargetActivity();
        } else {
            launchTargetActivity(mClass);
        }
    }

    @SuppressLint("WrongConstant")
    protected void launchTargetActivity() {
       PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(mDexPath,1);
       if (packageInfo.activities != null && packageInfo.activities.length > 0) {
           String activityName = packageInfo.activities[0].name;
           mClass = activityName;
           launchTargetActivity(mClass);
       }
    }

    protected void launchTargetActivity(String className) {
        Log.d(TAG,"start launchTargetActivity className: " + className);

        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(mDexPath,mCacheDir,null,localClassLoader);
        try {
            Class<?> localClass = dexClassLoader.loadClass(className);
            Constructor<?> localConstrucor = localClass.getConstructor(new Class[]{});
            Object instance = localConstrucor.newInstance(new Object[]{});
            Log.d(TAG,"instance: " + instance);
            Method setProxy = localClass.getMethod("setProxy",new Class[]{Activity.class});
            setProxy.setAccessible(true);
            setProxy.invoke(instance,new Object[]{this});

            Method onCreate = localClass.getDeclaredMethod("onCreate",new Class[]{Bundle.class});
            onCreate.setAccessible(true);
            Bundle bundle = new Bundle();
            bundle.putInt(FROM,FROM_EXTERNAL);
            onCreate.invoke(instance,new Object[]{bundle});





        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
