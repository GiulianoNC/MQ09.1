package com.quantum.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

//creacion de la base de datos
public class DbHelper extends SQLiteOpenHelper {

    private static final int  DATABASE_VERSION = 1;
    private static final String  DATABASE_NOMBRE = "tabla.db";
    public  static  final String TABLE= "t_tabla";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE " +  TABLE  + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT  NOT NULL," +
                "item TEXT  NOT NULL," +
                "pallet TEXT NOT NULL," +
                "resultado TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE  " + TABLE);
        onCreate(sqLiteDatabase);
    }
}
