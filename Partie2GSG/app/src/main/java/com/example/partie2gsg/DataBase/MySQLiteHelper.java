package com.example.partie2gsg.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_SUCCURSALES = "succursales";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDRESS = "address";

    public static final String COLUMN_TEL = "tel";

    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String DATABASE_NAME = "succursales.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_SUCCURSALES + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ADDRESS + " TEXT NOT NULL, "
                    + COLUMN_TEL + " TEXT NOT NULL, "
                    + COLUMN_LATITUDE + " REAL NOT NULL, "
                    + COLUMN_LONGITUDE + " REAL NOT NULL"
                    + ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        Log.w(
                MySQLiteHelper.class.getName(),
                "Mise à niveau de la base de données à partir de la version "
                        + oldVersion + " à " + newVersion
                        + ", qui détruira toutes les anciennes données"
        );

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUCCURSALES);

        onCreate(db);
    }
}
