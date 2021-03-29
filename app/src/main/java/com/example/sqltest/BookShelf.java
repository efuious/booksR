package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class BookShelf extends AppCompatActivity {

    private List<JSONObject> string = new LinkedList<>();
    private ListView bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        Intent intent = getIntent();

        init(intent.getStringExtra("tag"));
    }

    public void init(String tag){
        System.out.println("进入BookShelf页");

        System.out.println("开始读取BookShelf数据表");
        DB_Demo db =  new DB_Demo(this);
        string =  db.get_table("param1=getSearchBook&param2=tag2&param3="+tag);
        bookList = findViewById(R.id.bs_booklist);

        Whale w = new Whale();
        w.DoWork(this,string,bookList);
    }
}