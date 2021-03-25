package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class    History extends AppCompatActivity {

//    private  MyDatabase mydb;
    TextView textView;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        init();
    }

    public void init(){
        System.out.println("进入history页");


        textView = findViewById(R.id.history_textview);

        System.out.println("开始读取history数据表");
        DB_Demo db =  new DB_Demo(this);
        List<String> list =  db.get_data("paran1=getHistory");
        Whale whale = new Whale();
//        for (int i=0;i<list.size();i++){
//            whale.Parsing(list.get(i));
//        }
//        String str = db.get_history();
        System.out.println("读取到的数据：");

    }
}
