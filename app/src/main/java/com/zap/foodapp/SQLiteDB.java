package com.zap.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zap.foodapp.MyCart.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 09-08-2017.
 */

public class SQLiteDB {
    public static String KEY_ID = "ID";
    public static final String KEY_NAME = "name";
    public static final String KEY_BASEPRICE = "basePrice";
    public static final String KEY_TAKEAWAYPRICE = "takeawayPrice";
    public static final String KEY_MAXQUANTITY = "MAXQUANTITY";
    public static final String KEY_CUSINE = "cusine";
    public static final String KEY_QUANTITY = "quantity";

    public static final String FB_ID = "id";
    public static final String FB_NAME = "name";
    public static final String FB_EMAIL = "email";
    public static final String FB_NUMBER = "phonenumber";


    public static final String APPUSER_NAME = "name";
    public static final String APPUSER_EMAIL = "email";
    public static final String APPUSER_NUMBER = "phonenumber";
    public static final String APPUSER_PASSWORD = "password";

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "SQLiteDB";


    private static final int DATABASE_VERSION = 3;

    private static final String CREATE_TABLE = "create table if not exists cart1 (ID text primary key, NAME text,BASEPRICE text,TAKEAWAYPRICE text,MAXQUANTITY text,CUSINE text," + KEY_QUANTITY + " integer);";
    private static final String TABLE_NAME = "cart1";

    private static final String CREATE_FB_TABLE = "create table if not exists fbuser (" + FB_ID + " text primary key," + FB_NAME + " text," + FB_EMAIL + " text," + FB_NUMBER + " text          );";
    private static final String FB_TABLE_NAME = "fbuser";

    private static final String CREATE_APP_TABLE = "create table if not exists appuser (" + APPUSER_NAME + " text," + APPUSER_EMAIL + " text," + APPUSER_NUMBER + " text," + APPUSER_PASSWORD + " text);";
    private static final String APP_TABLE_NAME = "appuser";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    private static SQLiteDB instance = null;

    public static SQLiteDB getInstance() {
        return instance;
    }

    public static void initialize(Context ctx) {
        if (null == instance)
            instance = new SQLiteDB(ctx);
    }

    public List<CartItem> getAllContacts() {
        List<CartItem> foodList = new ArrayList<CartItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartItem food = new CartItem(
                        cursor.getString(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY)),
                        cursor.getInt(cursor.getColumnIndex(KEY_MAXQUANTITY)));
                // Adding food to list
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        // return contact list
        return foodList;
    }

    private SQLiteDB(Context ctx) {

        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        db = DBHelper.getWritableDatabase();


    }

    public boolean ifExist(String id) {
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + KEY_ID + " = " + id, null);
        return cursor.moveToFirst();
    }

    public void quantity(String id, int quantity) {
        db.execSQL("update " + TABLE_NAME + " set " + KEY_QUANTITY + " = " + quantity + " where " + KEY_ID + " = " + id);
    }

    public Boolean insertFbData(String ids, String names, String emails, String phones) {
        ContentValues values = new ContentValues();
        values.put(String.valueOf(FB_ID), ids);
        values.put(FB_NAME, names);
        values.put(FB_EMAIL, emails);
        values.put(String.valueOf(FB_NUMBER), phones);

        db.insert(FB_TABLE_NAME, null, values);
        return null;
    }

    public Boolean insertAppData(String appName, String appEmail, String appPhone, String appPassword) {
        ContentValues values = new ContentValues();
        values.put(APPUSER_NAME, appName);
        values.put(APPUSER_EMAIL, appEmail);
        values.put(APPUSER_NUMBER, appPhone);
        values.put(APPUSER_PASSWORD, appPassword);

        db.insert(APP_TABLE_NAME, null, values);
        return null;
    }

    public void insert(ContentValues values) {
        db.insert(TABLE_NAME, null, values);

    }

    public int countRecords() {

        String SqlCount = "Select * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(SqlCount, null);

        int cnt = cursor.getCount();

        cursor.close();

        return cnt;

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_FB_TABLE);
                db.execSQL(CREATE_APP_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + FB_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE_NAME);
            onCreate(db);
        }
    }

    //---Delete All Data from table in SQLite DB---
    public SQLiteDB deleteAll() {
        db.delete(TABLE_NAME, null, null);
        db.delete(FB_TABLE_NAME, null, null);
        db.delete(APP_TABLE_NAME, null, null);
        return null;
    }

}
