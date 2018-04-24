package com.root.book_fragment.entity;

import java.io.Serializable;

/**
 * Created by root on 2018-04-23.
 */

public class Person implements Serializable {
    private String name;
    private int age;
    private String code;
    private String sex;

    private String first_letter;
    private int _id;

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Person(){

    }

    public Person(String name,int age,String code){
        this.name = name;
        this.age = age;
        this.code = code;
    }

    public Person(String name,String sex,int age,String code){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.code = code;
    }
}
