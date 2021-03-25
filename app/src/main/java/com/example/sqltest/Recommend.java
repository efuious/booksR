package com.example.sqltest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Recommend extends AppCompatActivity {

    private  List<JSONObject> string = new LinkedList<>();
    private ListView recommendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        init();
        DoWork();

    }

    private void DoWork() {
        System.out.println("开始写入recommend数据...");
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
                System.out.println(id + "\t" + title + "\t" + article);

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

    public void init(){
        System.out.println("进入Recommend页");

        System.out.println("开始读取recommend数据表");
        DB_Demo db =  new DB_Demo(this);
        string =  db.get_table("param1=getRecommend");
        recommendList = findViewById(R.id.userlist);
//        for (int i=0;i<list.size();i++){
//        }
    }
}