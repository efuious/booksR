package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import net.sf.json.JSONObject;
import java.util.List;


public class    History extends AppCompatActivity {

//    private  MyDatabase mydb;
    TextView date,title,article;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        init();
    }

    public void init(){
        System.out.println("进入history页");
        date = findViewById(R.id.history_date);
        title = findViewById(R.id.history_title);
        article = findViewById(R.id.history_article);

        System.out.println("开始读取history数据表");
        DB_Demo db =  new DB_Demo(this);
        List<JSONObject> list =  db.get_table("param1=getHistory");
        date.setText(list.get(0).getString("id"));
        title.setText(list.get(0).getString("title"));
        article.setText(list.get(0).getString("article"));
    }
}
