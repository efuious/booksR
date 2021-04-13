package com.example.sqltest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Shelf extends Activity {

    private ExpandableListView tagList;
    private String[][] childs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf);

        Init();
    }

    public void getClick(int pos1,int pos2){
        System.out.println(pos1+"\t"+pos2+"");
        System.out.println(childs[pos1][pos2]);
        Intent booklist = new Intent(Shelf.this,BookShelf.class);
        booklist.putExtra("tag",childs[pos1][pos2]);
        startActivity(booklist);
    }

    public void Init(){
        final DB_Demo mydb = new DB_Demo(this);
        tagList = findViewById(R.id.shelflist);
        final Context t = this;
        final Object o = new Object();
        final ExpandableListviewAdapter[] adapter = new ExpandableListviewAdapter[1];
        Thread thread = new Thread(){
            @Override
            public void run() {
                List<JSONObject> list =  mydb.Get_table("param1=getTag");

                childs = new String[list.size()][];
                for(int i=0;i<list.size();i++){
                    childs[i] =list.get(i).getString("subTag").split(" ");// item;
                }
                String[] parents = new String[list.size()];
                for (int j=0;j<list.size();j++) {
                    parents[j] = list.get(j).getString("mainTag");
                }

                adapter[0] =new ExpandableListviewAdapter(t,parents,childs);
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
            tagList.setAdapter(adapter[0]);
        }catch (Exception e){
            System.out.println("子线程错误！");
        }



        tagList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                System.out.println("点击事件");
                getClick(groupPosition,childPosition);
                return true;
            }
        });
    }
}