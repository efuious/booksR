package com.example.sqltest;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Recommend extends Activity {

    private  List<JSONObject> string = new LinkedList<>();
    private ListView recommendList;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Intent intent = getIntent();
        String table = intent.getStringExtra("table");

        init(table);
        DoWork();

    }

    private void DoWork() {
        System.out.println("开始写入数据...");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < string.size(); i++) {
            Map<String, Object> listitem = new HashMap<String, Object>();
            String id = "第"+string.get(i).getString("id")+"号";
            String title = string.get(i).getString("title");
            String article =  string.get(i).getString("article");
            System.out.println("获取数据："+id+" "+title+" "+article);
            listitem.put("id",id);
            listitem.put("title", title);
            listitem.put("article",article);
            mapList.add(listitem);
        }

        SimpleAdapter simpleAdapter;
        simpleAdapter = new SimpleAdapter(this, mapList, R.layout.simple_adapter, new String[]{"id", "title"}, new int[]{R.id.tv_id, R.id.tv_title,});
        recommendList.setAdapter(simpleAdapter);

        recommendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long l) {
                final String id = string.get(position).getString("id");
                final String title = string.get(position).getString("title");
                final String article = string.get(position).getString("article");
                new AlertDialog.Builder(Recommend.this).setTitle("第"+id+"号 "+title).setMessage(article).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }

    public void init(final String table){
        title = findViewById(R.id.r_title);
        title.setText(table);
        final Object o = new Object();
        recommendList = findViewById(R.id.userlist);
        System.out.println("进入ita页");

        System.out.println("开始读取数据表: "+table);
        final DB_Demo db =  new DB_Demo(this);
        Thread thread = new Thread(){
            @Override
            public void run() {
                string =  db.Get_table("param1=getIta&param2="+table);
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