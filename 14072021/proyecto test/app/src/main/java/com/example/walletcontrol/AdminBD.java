package com.example.walletcontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public  class AdminBD extends SQLiteOpenHelper {
    public AdminBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Cuentas (codigo INTEGER PRIMARY KEY AUTOINCREMENT, descripC varchar,descripL varchar,estado boolean,diasdepago int)");
        db.execSQL("create table Gastos (idgasto INTEGER PRIMARY KEY AUTOINCREMENT , idcuenta int,descripC varchar,monto int, fechapago date,fechareg date,obs varchar)");
        db.execSQL("create table Ingresos (idingresos INTEGER PRIMARY KEY AUTOINCREMENT, descripC varchar, monto int, fechapago date,fechareg date,obs varchar)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
