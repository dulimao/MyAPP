package com.example.myapp.ipc.content_provider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapp.Book;
import com.example.myapp.R;
import com.example.myapp.ipc.other.User;

/**
 * 跨进程通信之ContentProvider
 */
public class ProviderActivity extends Activity {

    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

    }

    public void test(View view) {
        Uri bookUri = Uri.parse("content://com.example.myapp.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","Android开发艺术探索");
        getContentResolver().insert(bookUri,values);

        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{"_id","name"},null,null,null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG,"query book: " + book.toString());
        }
        bookCursor.close();

        Uri userUri = BookProvider.USER_CONTENT_URI;
        Cursor userCursor = getContentResolver().query(userUri,new String[]{"_id","name","sex"},null,null,null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(userCursor.getColumnIndex("name"));
            user.userSex = userCursor.getInt(2);
            Log.d(TAG,"query user: " + user.toString());
        }
        userCursor.close();

    }
}
