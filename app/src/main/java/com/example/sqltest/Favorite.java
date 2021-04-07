 package com.example.sqltest;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Favorite extends Activity {

    private ListView favoritelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        init();
    }

    protected void onRestart() {
        super.onRestart();
        init();
    }

    public void favoriteList(final List<JSONObject> list, List<JSONObject> strings , ListView table) {
        System.out.println("开始写入booklist数据...");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < strings.size(); i++) {
            Map<String, Object> listitem = new HashMap<String, Object>();
            String publish = strings.get(i).getString("publish") ;
            int id = strings.get(i).getInt("id") ;
            String title = strings.get(i).getString("title");
            String author = strings.get(i).getString("author");
            listitem.put("publish", publish);
            listitem.put("title", title);
            listitem.put("author", author);
            mapList.add(listitem);
        }

        SimpleAdapter simpleAdapter;
        simpleAdapter = new SimpleAdapter(this, mapList, R.layout.book_adapter, new String[]{"title","author","publish"}, new int[]{R.id.ba_bookname, R.id.ba_author,R.id.ba_publish});
        table.setAdapter(simpleAdapter);

        final List<JSONObject> string = strings;
        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long l) {
                Intent bookdetail = new Intent(Favorite.this,BookDetail.class);
                String str = string.get(position).toString();
                System.out.println("点击对象："+str);
                bookdetail.putExtra("string",str);
                startActivity(bookdetail);
            }
        });
        table.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(Favorite.this).setTitle(string.get(position).getString("title")).setMessage("移除收藏夹？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("删除成功！");
                        DB_Demo db_demo = new DB_Demo(Favorite.this);
                        boolean status = false;
                        status = db_demo.deleteFavorite(list.get(position).getInt("id"));
                        if (status){
                            onRestart();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                return false;
            }
        });
    }

    public void init(){
        MyDatabase mydb = new MyDatabase(this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        int userid = mydb.get_userid(db);
        String pswd = mydb.get_pswd(db);
        db.close();

        if(userid == 0 || pswd.equals("")){
            Toast.makeText(this,"获取用户信息失败! 请重新登录",Toast.LENGTH_SHORT).show();
            return;
        }

        DB_Demo db_demo = new DB_Demo(this);
        List<JSONObject> list = db_demo.getFavorite(userid,pswd);
        System.out.println(list);

        favoritelist = findViewById(R.id.favoritelist);

        List<JSONObject> booklist = new LinkedList<>();
        for(int i=0;i<list.size();i++){
            JSONObject js = db_demo.getSearchBook("id",list.get(i).getInt("bookid")+"").get(0);
            booklist.add(js);
        }
        favoriteList(list,booklist,favoritelist);
    }
}