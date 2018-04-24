package com.root.book_fragment.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.root.book_fragment.entity.Person;
import com.root.book_fragment.util.StudySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2018-04-23.
 */

public class PersonDao1 {
    Context context;
    StudySQLiteOpenHelper studySQLiteOpenHelper;

    public  PersonDao1(Context context){
        this.context = context;
        studySQLiteOpenHelper = new StudySQLiteOpenHelper(context,"androidStudy.db",7);
    }

    public void addPerson(Person person){
        SQLiteDatabase db = studySQLiteOpenHelper.getWritableDatabase();
        String sql = "insert into person(_id,name,sex,age,code)";
        db.execSQL(sql,new Object[]{person.getName(),
                person.getSex(),person.getAge(),person.getCode()});

    }

    public void deletePerson(Person person){
        SQLiteDatabase db = studySQLiteOpenHelper.getWritableDatabase();
        String sql = "delete from person where _id=?";
        db.execSQL(sql,new Object[]{person.get_id()});
    }

    public void updatePerson(Person person){
        SQLiteDatabase db = studySQLiteOpenHelper.getWritableDatabase();
        String sql = "update person set name=?,sex=?,age=?,code=? where _id=?";
        db.execSQL(sql,new Object[]{person.getName(),person.getSex(),
                person.getAge(),person.getCode(),person.get_id()});
    }

    public List<Person> getAllPerson(){
        List<Person> persons = new ArrayList<Person>();
        SQLiteDatabase db = studySQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from person";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String code = cursor.getString(cursor.getColumnIndex("code"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            Person person = new Person(name,sex,age,code);
            person.set_id(_id);
            persons.add(person);
        }
        return persons;
    }
}
