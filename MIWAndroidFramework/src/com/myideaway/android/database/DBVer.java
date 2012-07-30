package com.myideaway.android.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-5-12
 * Time: PM11:28
 */
public abstract class DBVer  {

    public void create(SQLiteDatabase db) throws  DatabaseException{
         onCreate(db);
    }

    public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws DatabaseException{
        onUpgrade(db, oldVersion, newVersion);
    }

    protected abstract void onCreate(SQLiteDatabase db) throws DatabaseException;

    protected abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws DatabaseException;
}
