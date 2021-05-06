package com.example.sqltest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import net.sf.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Newest extends Activity {


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
        final Object o = new Object();

        final DB_Demo db =  new DB_Demo(this);
        Thread thread = new Thread(){
            @Override
            public void run() {
                list = db.GetSearchBook("title", "");
                synchronized (o){
                    System.out.println("等待中...");
                    o.notify();
                }
            }
        };
        try{
            thread.start();
            synchronized (o){
                o.wait(500);
            }
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
        Collections.reverse(list);
        bookList = findViewById(R.id.newest_booklist);
        new Whale().DoWork(this,list ,bookList);
    }
}