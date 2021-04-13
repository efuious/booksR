package com.example.sqltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchPage extends Activity implements View.OnClickListener {

    private List<JSONObject> string = new LinkedList<>();
    private ListView searchList;
    private EditText input_search;
    private Button btn_search;
    private Whale whale;

    private List<String> list = new ArrayList<String>();
    private Spinner spinnertext;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Intent intent = getIntent();
        whale = new Whale();
        init(intent.getStringExtra("value"));
        whale.DoWork(this,string,searchList);
    }


    @Override
    public void onClick(View view){
        getSearch();
        whale.DoWork(this,string,searchList);
    }

    private void getSearch(){
        int index = spinnertext.getSelectedItemPosition();
        String col = "";
        if (index==0){
            System.out.println("选择标题");
            col +=  "title";
        }else if(index == 1){
            System.out.println("选择作者");
            col +=  "author";
        }
        final DB_Demo db = new DB_Demo(this);
        final Object o = new Object();
        final String colname  = col;
        Thread thread = new Thread(){
            @Override
            public void run() {
                string = db.GetSearchBook(colname, input_search.getText().toString());
                synchronized (o) {
                    System.out.println("等待中...");
                    o.notify();
                }
            }};
        try{
            thread.start();
            synchronized (o){
                o.wait(2000);
            }
        }catch (Exception e){
            System.out.println("子线程错误！");
        }
    }

    public void init(String value) {
        list.add("标题");
        list.add("作者");
        System.out.println("进入Search页");
        spinnertext = findViewById(R.id.search_spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertext.setAdapter(adapter);
        input_search = findViewById(R.id.search_input_search);
        input_search.setText(value);
        searchList = findViewById(R.id.searchlist);
        btn_search = findViewById(R.id.search_btn_search);
        btn_search.setOnClickListener(this);
        System.out.println("开始读取booklist数据表");
        getSearch();
    }
}