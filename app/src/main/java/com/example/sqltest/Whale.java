package com.example.sqltest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Whale {

    public List<String> Parsing(String strs){
        String[] split = strs.split("\t");

        List<String> string = new LinkedList<>();
        for (int i=0;i<split.length;i++){
            string.add(split[i].trim());
            System.out.println("解析："+split[i].trim());
        }
        return string;
    }

    public int checkSex(String sex){
        if (sex.equals("男")){
            return 1;
        }
        else if(sex.equals("女")){
            return 0;
        }
        return 2;
    }

    public JSONObject String2Json(String string){
        System.out.println("转换字符串："+string);
        return JSONObject.fromObject(string);
    }

    public List<JSONObject> String2Jsons(String string){
        String[] strs = string.split("\\{");
        List<JSONObject> json = new LinkedList<>();
        for (int i=1;i<strs.length;i++){
            String s2j = "{"+strs[i].replace("},","}").trim();
            System.out.println("转换字符串2："+s2j);
            JSONObject jo =JSONObject.fromObject(s2j);
            json.add(jo);
        }
        return json;
    }

    public void DoWork(final Context context, List<JSONObject> strings , ListView table) {
        System.out.println("开始写入booklist数据...");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < strings.size(); i++) {
            Map<String, Object> listitem = new HashMap<String, Object>();
            String publish = strings.get(i).getString("publish") ;
            String title = strings.get(i).getString("title");
            String author = strings.get(i).getString("author");
            listitem.put("publish", publish);
            listitem.put("title", title);
            listitem.put("author", author);
            mapList.add(listitem);
        }

        SimpleAdapter simpleAdapter;
        simpleAdapter = new SimpleAdapter(context, mapList, R.layout.book_adapter, new String[]{"title","author","publish"}, new int[]{R.id.ba_bookname, R.id.ba_author,R.id.ba_publish});
        table.setAdapter(simpleAdapter);

        final List<JSONObject> string = strings;
        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long l) {
                Intent bookdetail = new Intent(context,BookDetail.class);
                String str = string.get(position).toString();
                System.out.println("点击对象："+str);
                bookdetail.putExtra("string",str);
                context.startActivity(bookdetail);
            }
        });

        table.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(context).setTitle(string.get(position).getString("title")).setMessage("添加收藏夹？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("开始添加");
                        DB_Demo db_demo = new DB_Demo(context);
                        boolean status = false;
                        MyDatabase mydb = new MyDatabase(context);
                        SQLiteDatabase db = mydb.getReadableDatabase();
                        String userid = mydb.get_userid(db)+"";
                        String pswd = mydb.get_pswd(db);
                        db.close();
                        String msg = db_demo.addFavorite(userid,pswd.trim(),string.get(position).getInt("id")+"");
                        switch (msg){
                            case "true":
                                Toast.makeText(context,"加入收藏夹成功！",Toast.LENGTH_SHORT).show(); break;
                            case "existed":
                                Toast.makeText(context,"已在收藏夹中",Toast.LENGTH_SHORT).show();break;
                            case "failed":
                                Toast.makeText(context,"请先登录！",Toast.LENGTH_SHORT).show();break;
                            default:
                            Toast.makeText(context,"加入收藏夹失败！",Toast.LENGTH_SHORT).show();break;
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
}