package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import net.sf.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Newest extends AppCompatActivity {


    private List<JSONObject> list = new LinkedList<>();
    private ListView bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newest);

        init();
    }

    public void init(){
        System.out.println("最近上新");

        DB_Demo db =  new DB_Demo(this);
        list =  db.getSearchBook("title","");
        Collections.reverse(list);
        bookList = findViewById(R.id.newest_booklist);
        new Whale().DoWork(this,list ,bookList);
    }
}