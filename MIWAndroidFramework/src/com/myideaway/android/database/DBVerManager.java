package com.myideaway.android.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-5-14
 * Time: PM2:01
 */
public class DBVerManager {
    private String verPackageName;

    public void createDB(SQLiteDatabase db, int ver) throws Exception {
        String className = verPackageName + ".v" + ver + ".DBVer" + ver;

        Class clz = Class.forName(className);
        DBVer dbVer = (DBVer) clz.newInstance();
        dbVer.create(db);
    }

    public void upgradeDB(SQLiteDatabase db, int oldVer, int newVer) throws Exception {
        int startVer =  oldVer + 1;

        for(int ver = startVer; ver <= newVer;ver++){
            String className = verPackageName +".v" + ver + ".DBVer" + ver;

            Class clz = Class.forName(className);
            DBVer dbVer = (DBVer) clz.newInstance();
            dbVer.upgrade(db, oldVer, newVer);
        }

    }

    public String getVerPackageName() {
        return verPackageName;
    }

    public void setVerPackageName(String verPackageName) {
        this.verPackageName = verPackageName;
    }
}
