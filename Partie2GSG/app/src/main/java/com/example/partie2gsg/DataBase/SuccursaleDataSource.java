
package com.example.partie2gsg.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.partie2gsg.DataBase.SuccursaleDomino;

import java.util.ArrayList;
import java.util.List;

public class SuccursaleDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private final String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ADDRESS,
            MySQLiteHelper.COLUMN_TEL,
            MySQLiteHelper.COLUMN_LATITUDE,
            MySQLiteHelper.COLUMN_LONGITUDE
    };

    public SuccursaleDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //Crée la succursale et l'ajoute a la base de données
    public SuccursaleDomino createSuccursale(
            String address,
            String tel,
            double latitude,
            double longitude) {

        // Vérifier si l'adresse existe déjà
        SuccursaleDomino existante = getSuccursaleByAddress(address);
        if (existante != null) {
            return existante; // ou return null; si tu préfères signaler le doublon
        }

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ADDRESS, address);
        values.put(MySQLiteHelper.COLUMN_TEL, tel);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);

        long insertId = database.insert(
                MySQLiteHelper.TABLE_SUCCURSALES,
                null,
                values
        );

        if (insertId == -1) {
            return null;
        }

        try (Cursor cursor = database.query(
                MySQLiteHelper.TABLE_SUCCURSALES,
                allColumns,
                MySQLiteHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(insertId)},
                null, null, null)) {

            if (cursor.moveToFirst()) {
                return cursorToSuccursale(cursor);
            }
        }

        return null;
    }

    //revoie les succursale par address pour eviter les doublons.
    public SuccursaleDomino getSuccursaleByAddress(String address) {
        try (Cursor cursor = database.query(
                MySQLiteHelper.TABLE_SUCCURSALES,
                allColumns,
                MySQLiteHelper.COLUMN_ADDRESS + " = ?",
                new String[]{address},
                null, null, null,
                "1")) {

            if (cursor.moveToFirst()) {
                return cursorToSuccursale(cursor);
            }
        }
        return null;
    }

    public void deleteSuccursale(SuccursaleDomino succursale) {
        long id = succursale.getId();

        database.delete(
                MySQLiteHelper.TABLE_SUCCURSALES,
                MySQLiteHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public List<SuccursaleDomino> getAllSuccursales() {
        List<SuccursaleDomino> succursales = new ArrayList<>();

        try (Cursor cursor = database.query(
                MySQLiteHelper.TABLE_SUCCURSALES,
                allColumns,
                null,
                null,
                null,
                null,
                null)) {

            while (cursor.moveToNext()) {
                succursales.add(cursorToSuccursale(cursor));
            }
        }

        return succursales;
    }

    private SuccursaleDomino cursorToSuccursale(Cursor cursor) {
        SuccursaleDomino succursale = new SuccursaleDomino();

        succursale.setId(cursor.getLong(0));
        succursale.setAddress(cursor.getString(1));
        succursale.setTel(cursor.getString(2));
        succursale.setLatitude(cursor.getDouble(3));
        succursale.setLongitude(cursor.getDouble(4));

        return succursale;
    }
}
