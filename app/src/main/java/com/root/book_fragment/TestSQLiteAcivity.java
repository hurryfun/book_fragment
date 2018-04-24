package com.root.book_fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.*;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.root.book_fragment.dao.PersonDao1;
import com.root.book_fragment.entity.Person;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2018-04-23.
 */

public class TestSQLiteAcivity extends AppCompatActivity {

    ListView listView;
    List<Person> persons;
    PersonDao1 personDao;
    BaseAdapter personAdapter;
    Map<String,Boolean> radioStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_sqlsql_lite);

        radioStates = new HashMap<String,Boolean>();
        personDao = new PersonDao1(this);
        listView  = findViewById(R.id.listView);
        persons = personDao.getAllPerson();
        initListView();


    }

    public void  initListView(){
        personAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return persons.size();
            }

            @Override
            public Object getItem(int position) {
                return persons.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                view = getLayoutInflater().inflate(R.layout.item_sqlite_listview,null);

                TextView textViewPerson_name = view.findViewById(R.id.textView_Person_name);
                TextView textViewPerson_sex = view.findViewById(R.id.textView_Person_sex);
                TextView textViewPerson_code = view.findViewById(R.id.textView_Person_code);
                TextView textViewPerson_age = view.findViewById(R.id.textView_Person_age);

                final Person person = (Person) getItem(position);
                textViewPerson_name.setText(person.getName());
                textViewPerson_age.setText(person.getAge()+"");
                textViewPerson_sex.setText(person.getSex());
                textViewPerson_code.setText(person.getCode());

                final RadioButton radioButton = view.findViewById(R.id.radioButton);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v instanceof RadioButton){
                            RadioButton radioButton = (RadioButton) v;
                            if (radioButton.isChecked()){
                                radioStates.put(String.valueOf(position),true);
                                for (String key : radioStates.keySet()){
                                    if (!key.equals(String.valueOf(position))){
                                        radioStates.put(key,false);
                                    }
                                }
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
                Boolean tempState = radioStates.get(String.valueOf(position));
                if (tempState!=null&&tempState){
                    radioButton.setChecked(true);
                    view.setBackgroundColor(Color.BLUE);
                }else{
                    radioButton.setChecked(false);
                    view.setBackgroundColor(Color.WHITE);
                }
                return view;
            }
        };
        listView.setAdapter(personAdapter);
    }

    public void addPerson(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增用户").create();

        View childView = getLayoutInflater().inflate(R.layout.layout_sqlite_alert,null);
        builder.setView(childView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Field field = null;

                try {
                    field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    System.out.println("22222222222222222222222");
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog,false);

                    System.out.println("111111111111111111111111");

                    AlertDialog ad;
                    if (dialog instanceof AlertDialog){
                        ad = (AlertDialog) dialog;
                        EditText editViewPerson_name = ad.findViewById(R.id.editViewPerson_name);
                        EditText editViewPerson_code = ad.findViewById(R.id.editViewPerson_code);
                        EditText editViewPerson_age = ad.findViewById(R.id.editViewPerson_age);
                        RadioGroup radioGroup_sex = ad.findViewById(R.id.radioGroup_sex);

                        if (TextUtils.isEmpty(editViewPerson_name.getText().toString())){
                            Toast.makeText(TestSQLiteAcivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(editViewPerson_code.getText().toString())){
                            Toast.makeText(TestSQLiteAcivity.this,"编号不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(editViewPerson_age.getText().toString())){
                            Toast.makeText(TestSQLiteAcivity.this,"年龄不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        Person person = new Person();
                        person.setAge(Integer.parseInt(editViewPerson_age.getText().toString()));
                        person.setName(editViewPerson_name.getText().toString());
                        person.setCode(editViewPerson_code.getText().toString());
                        RadioButton radioButton = radioGroup_sex.
                                findViewById(radioGroup_sex.getCheckedRadioButtonId());
                        person.setSex(radioButton.getText().toString());
                        personDao.addPerson(person);

                        persons = personDao.getAllPerson();
                        radioStates = new HashMap<String, Boolean>();
                        personAdapter.notifyDataSetChanged();
                        field.set(dialog,true);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }


    public void deletePerson(View view){
        int position = -1;

        for (int i = 0; i < listView.getChildCount(); i++){
            View childView = listView.getChildAt(i);
            RadioButton radioButton = childView.findViewById(R.id.radioButton);
            if (radioButton.isChecked()){
                position = i;
                break;
            }
        }

        if (position != -1){
            Person person = persons.get(position);
            personDao.deletePerson(person);
            persons = personDao.getAllPerson();
            radioStates = new HashMap<String, Boolean>();
            personAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this,"请选择要删除的行",Toast.LENGTH_SHORT).show();
        }
    }


    public void updataPerson(){
        int position = -1;
        for (int i = 0; i < listView.getChildCount(); i++) {
            View childView = listView.getChildAt(i);
            RadioButton radioButton = childView.findViewById(R.id.radioButton);
            if (radioButton.isChecked()) {
                position = i;
                break;
            }
        }

            if (position != -1){
                final  Person person = persons.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("修改用户").create();
                View childView = getLayoutInflater().inflate(R.layout.layout_sqlite_alert,null);

                EditText editTextPerson_name =
                        childView.findViewById(R.id.editViewPerson_name);
                EditText editTextPerson_code=
                        childView.findViewById(R.id.editViewPerson_code);
                EditText editTextPerson_age =
                        childView.findViewById(R.id.editViewPerson_age);
                RadioGroup radioGroup_sex =
                        childView.findViewById(R.id.radioGroup_sex);

                editTextPerson_name.setText(person.getName());
                editTextPerson_age.setText(person.getAge());
                editTextPerson_code.setText(person.getCode());
                if (person.getSex().equals("男")){
                    ((RadioButton)radioGroup_sex.getChildAt(0)).setChecked(true);
                }else {
                    ((RadioButton)radioGroup_sex.getChildAt(1)).setChecked(true);
                }



                builder.setView(childView);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog,false);

                            AlertDialog ad;
                            if (dialog instanceof AlertDialog){
                                ad = (AlertDialog) dialog;
                                EditText editViewPerson_name = ad.findViewById(R.id.editViewPerson_name);
                                EditText editViewPerson_code = ad.findViewById(R.id.editViewPerson_code);
                                EditText editViewPerson_age = ad.findViewById(R.id.editViewPerson_age);
                                RadioGroup radioGroup_sex = ad.findViewById(R.id.radioGroup_sex);

                                if (TextUtils.isEmpty(editViewPerson_name.getText().toString())){
                                    Toast.makeText(TestSQLiteAcivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(editViewPerson_age.getText().toString())){
                                    Toast.makeText(TestSQLiteAcivity.this,"年龄不能为空",Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                person.setAge(Integer.parseInt(editViewPerson_age.getText().toString()));
                                person.setName(editViewPerson_name.getText().toString());
                                RadioButton radioButton = radioGroup_sex.
                                        findViewById(radioGroup_sex.getCheckedRadioButtonId());
                                person.setSex(radioButton.getText().toString());
                                personDao.updatePerson(person);

                                persons = personDao.getAllPerson();
                                radioStates = new HashMap<String, Boolean>();
                                personAdapter.notifyDataSetChanged();
                                field.set(dialog,true);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                builder.show();

            }else {
                Toast.makeText(this,"请选择要删除的行",Toast.LENGTH_SHORT).show();
            }
    }
}
