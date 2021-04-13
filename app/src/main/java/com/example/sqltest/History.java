package com.example.sqltest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import net.sf.json.JSONObject;
import java.util.List;


public class    History extends Activity {

    TextView date,title,article;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        init();
    }

    public void init() {
        System.out.println("进入history页");
        date = findViewById(R.id.history_date);
        title = findViewById(R.id.history_title);
        article = findViewById(R.id.history_article);

        System.out.println("开始读取history数据表");
        final DB_Demo db = new DB_Demo(this);
        final Object o = new Object();
        Thread thread = new Thread(){
            @Override
            public void run() {
            List<JSONObject> list = db.Get_table("param1=getHistory");
            if (list.size() != 0) {
                date.setText(list.get(0).getString("id"));
                title.setText(list.get(0).getString("title"));
                article.setText(list.get(0).getString("article"));
            }
            synchronized (o){
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
    }
}
