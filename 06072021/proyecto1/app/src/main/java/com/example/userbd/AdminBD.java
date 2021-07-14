package com.example.userbd;

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
        db.execSQL("create table productos (codigo int primary key, descrip varchar,precio int,cantidad int)");
        db.execSQL("create table inventario (codigo int , stock int,tipo varchar, entidad int)");
        db.execSQL("create table cliente (codigo int primary key, nombre varchar,estado boolean)");
        db.execSQL("create table proveedor (codigo int primary key, razonsoc varchar,estado boolean)");
        db.execSQL("create table compra (codigo int primary key, fehca date,cantidad int,idprov int)");
        db.execSQL("create table venta (codigo int primary key, fehca date,cantidad int,idcliente int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
