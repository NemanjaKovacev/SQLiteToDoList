package com.example.nemanja.sqlitetodolist.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "database";
    private static final String DB_NAME = "todo_db";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "todo_table";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";

    private static final String TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        database.execSQL(TABLE_QUERY);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
