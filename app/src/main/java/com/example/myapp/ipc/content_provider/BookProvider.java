package com.example.myapp.ipc.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * ContentProvider底层也是Binder,insert,query,delete,update均运行在Binder线程池中
 */
public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";
    public static final String AUTHORITY = "com.example.myapp.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDB;



    @Override
    public boolean onCreate() {
        mContext = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDB = new DBOpenHelper(mContext).getWritableDatabase();
        mDB.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
        mDB.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
        mDB.execSQL("insert into book values(1,'Android');");
        mDB.execSQL("insert into user values(1,'jake',2);");
    }

   
    @Override
    public Cursor query(Uri uri,String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG,"query : " + uri + " threadName: " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null)
            throw new IllegalArgumentException("Unsupported Uri : " + uri);
        return mDB.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    
    @Override
    public String getType( Uri uri) {
        Log.d(TAG,"getType()");
        return null;
    }

    
    @Override
    public Uri insert( Uri uri, ContentValues values) {
        Log.d(TAG,"insert()");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDB.insert(table,null,values);
        return uri;
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG,"delete()");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        int count = mDB.delete(table,selection,selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG,"update()");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        int row = mDB.update(table,values,selection,selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return row;
    }

    //根据Uri得到UriCode,有了UriCode就可以知道调用者想查找的表是哪个
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DBOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }
}
