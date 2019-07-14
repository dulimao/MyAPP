package com.example.corelibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * author : dulimao
 * e-mail : dulimao@yuewen.com
 * date   : 2019/7/819:31
 * desc   :
 * version: 1.0
 */
public class Utils {


    public static String copyFiles(Context context,String fileName) {
        File dir = getCacheDir(context);
        String filePath = dir.getAbsolutePath() + File.separator + fileName;
        try {
            File desFile = new File(filePath);
            if (!desFile.exists()) {
                desFile.createNewFile();
                copyFiles(context,fileName,desFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


    public static void copyFiles(Context context,String fileName,File desFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getApplicationContext().getAssets().open(fileName);
            out = new FileOutputStream(desFile.getAbsolutePath());
            byte[] bytes = new byte[1024];
            int len;
            while ( (len = in.read(bytes)) != -1) {
                out.write(bytes,0,len);
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取缓存路径
     * @param context
     * @return
     */
    public static File getCacheDir(Context context) {
        File cache;
        if (hasExternalStorage()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }


    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
