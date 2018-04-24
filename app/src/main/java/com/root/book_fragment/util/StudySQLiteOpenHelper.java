package com.root.book_fragment.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 2018-04-23.
 */

public class StudySQLiteOpenHelper extends SQLiteOpenHelper {
    String dataBase_name;
    Context context;
    String create_TABLE_sql = "create table person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name varchar(30),age integer,sex varchar(30),code varchar(30))";
    String delete_Sql = "delete from person";

    public StudySQLiteOpenHelper(Context context, String name, int version){
        super(context,name,null,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_TABLE_sql);
        db.execSQL("create table alarm(alarm_id integer primary key autoincrement," +
                "alarm_name varchar(30),alarm_hour integer,alarm_minute integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table person");
        db.execSQL(create_TABLE_sql);
        db.execSQL("drop table alarm");
        db.execSQL("create table alarm(alarm_id integer primary key autoincrement," +
                "alarm_name varchar(30),alarm_hour integer,alarm_minute integer)");
    }
}
