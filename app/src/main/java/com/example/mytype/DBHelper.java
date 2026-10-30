package com.example.mytype;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "workout.db";
    private static final int    DB_VERSION = 1;
    private static final String TABLE      = "exercises";
    private static final String COL_ID     = "id";
    private static final String COL_NAME   = "name";
    private static final String COL_DUR    = "duration";
    private static final String COL_DONE   = "completed";
    private static final String COL_GROUP  = "muscle_group";
    public DBHelper(Context c) { super(c, DB_NAME, null, DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" +
                COL_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_DUR  + " INTEGER, " +
                COL_DONE + " INTEGER DEFAULT 0, " +
                COL_GROUP+ " TEXT)");
    }
    @Override public void onUpgrade(SQLiteDatabase db, int o, int n) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE); onCreate(db);
    }

    // 1) إضافة
    public long insert(String name, int duration, boolean completed, String group) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_DUR, duration);
        cv.put(COL_DONE, completed ? 1 : 0);
        cv.put(COL_GROUP, group);
        long id = db.insert(TABLE, null, cv);
        db.close();
        return id;
    }

    // 2) قراءة كل العناصر بترتيب أبجدي
    public List<Exercise> getAllOrdered() {
        return query(null, null);
    }

    // 3) بحث باستخدام LIKE و ?
    public List<Exercise> search(String keyword) {
        return query(COL_NAME + " LIKE ?", new String[]{"%" + keyword + "%"});
    }

    private List<Exercise> query(String selection, String[] args) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, null, selection, args,
                null, null, COL_NAME + " ASC");
        while (c.moveToNext()) {
            list.add(new Exercise(
                    c.getInt(c.getColumnIndexOrThrow(COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getInt(c.getColumnIndexOrThrow(COL_DUR)),
                    c.getInt(c.getColumnIndexOrThrow(COL_DONE)) == 1,
                    c.getString(c.getColumnIndexOrThrow(COL_GROUP))
            ));
        }
        c.close(); db.close();
        return list;
    }

    // 4) تعديل حقل نعم/لا
    public int updateCompleted(int id, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_DONE, completed ? 1 : 0);
        int rows = db.update(TABLE, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // 5) حذف
    public int delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }}