package com.example.sqltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class BookShelf extends Activity {

    private List<JSONObject> string = new LinkedList<>();
    private ListView bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        Intent intent = getIntent();

        init(intent.getStringExtra("tag"));
    }

    public void init(final String tag){
        System.out.println("进入BookShelf页");

        System.out.println("开始读取BookShelf数据表");
        final DB_Demo db =  new DB_Demo(this);
        final Object o = new Object();
        Thread thread = new Thread(){
            @Override
            public void run() {
                string =  db.Get_table("param1=getSearchBook&param2=tag2&param3="+tag);
                synchronized (o) {
                    System.out.println("等待中...");
                    o.notify();
                }
             }
        };
        try{
            thread.start();
            synchronized (o){
                o.wait(100);
            }
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
        bookList = findViewById(R.id.bs_booklist);
        System.out.println("子线执行中...");
        Whale w = new Whale();
        w.DoWork(this,string,bookList);
    }
}