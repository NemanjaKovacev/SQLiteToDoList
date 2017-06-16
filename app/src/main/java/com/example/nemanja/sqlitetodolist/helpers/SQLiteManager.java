package com.example.nemanja.sqlitetodolist.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nemanja.sqlitetodolist.models.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager {
    private final SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase database;
    private final Context context;

    public SQLiteManager(Context context) {
        this.context = context;
        databaseHelper = new SQLiteHelper(context);
    }

    public void open() {
        Log.e("DB", "Database open!");
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        Log.e("DB", "Database close!");
        databaseHelper.close();
    }

    public long insertItem(@NonNull Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.KEY_TITLE, item.title);
        return database.insert(SQLiteHelper.TABLE_NAME, null, contentValues);
    }

    public void update(@NonNull Item item, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.KEY_TITLE, item.title);
        database.update(SQLiteHelper.TABLE_NAME, contentValues, SQLiteHelper.KEY_ID + " = " + id, null);
    }

    @NonNull
    public List<Item> getAllItems() {

        List<Item> itemsList = new ArrayList<>();
        String select = "SELECT * FROM " + SQLiteHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.id = cursor.getInt(0);
                item.title = cursor.getString(1);
                Log.i("SQLiteDatabase", item.title + "");
                itemsList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return itemsList;
    }

    public int remove(int id) {
        return database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.KEY_ID + " = " + id, null);
    }
}
