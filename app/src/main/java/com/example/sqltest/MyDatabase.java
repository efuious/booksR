package com.example.sqltest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, "user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id integer,name varchar(20),sex int,birthday varchar(22),pswd varchar(40))";
        String sql2 = "create table usertag(id integer,tag1 varchar(20),tag2 varchar(20),tag3 varchar(20),tag4 varchar(20),tag5 varchar(20))";
        db.execSQL(sql);
        System.out.println("创建表：usertag");
        db.execSQL(sql2);
        System.out.println("创建完成");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void add_data(SQLiteDatabase db,int id, String username, int sex, String birthday,String pswd){
        delete_all(db);
        System.out.println("开始为本地数据库添加数据...");
        ContentValues values = new ContentValues();
        ContentValues tags = new ContentValues();
        values.put("id",id);
        values.put("name", username);
        values.put("sex", sex);
        values.put("birthday",birthday );
        values.put("pswd",pswd);
        tags.put("id",id);
        db.insert("user", null, values);
        db.insert("usertag",null,tags);
    }

    private void delete_all(SQLiteDatabase db) {
        db.delete("user",null,null);
        db.delete("usertag",null,null);
//        db.close();
    }

    public void ____delete_all(SQLiteDatabase db){
        db.delete("user",null,null);
        db.close();
    }

    public void update(SQLiteDatabase db,int id, String username, int sex, String birthday,String pswd) {
            ContentValues values = new ContentValues();
            values.put("name", username);
            values.put("sex", sex);
            values.put("birthday", birthday);
            values.put("pswd", pswd);
            db.update("user", values, "id = ?", new String[]{id + ""});
            db.close();
    }

    public void updateUser(SQLiteDatabase db, int id, String col, String value){
        ContentValues values = new ContentValues();
        values.put(col, value);
        db.update("user", values, "id = ?", new String[]{id + ""});
    }

    public int get_userid(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from user",null);
        if (cursor.moveToPosition(0) != true) {
            return 0;
        }
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        return id;
    }

    public String get_pswd(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from user",null);
        if (cursor.moveToPosition(0) != true) {
            return "";
        }
        String pswd = cursor.getString(cursor.getColumnIndex("pswd"));
        cursor.close();
        return pswd;
    }

    public void setTag(SQLiteDatabase db,String tagn,String tagname,int userid){
        System.out.println("设置"+tagn+"为： "+tagname);
        ContentValues values = new ContentValues();
        values.put(tagn,tagname.trim());
        db.update("userTag",values,"id =?",new String[]{userid+""});
    }

    public List<String> getTag(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from usertag",null);
        if (cursor.moveToPosition(0) != true) {
            return null;
        }
        List<String> list = new LinkedList<>();
        list.add(cursor.getString(cursor.getColumnIndex("tag1")));
        list.add(cursor.getString(cursor.getColumnIndex("tag2")));
        list.add(cursor.getString(cursor.getColumnIndex("tag3")));
        list.add(cursor.getString(cursor.getColumnIndex("tag4")));
        list.add(cursor.getString(cursor.getColumnIndex("tag5")));

        return list;
    }

    public JSONObject getUser(SQLiteDatabase db) throws JSONException {
        Cursor cursor=db.rawQuery("select * from user",null);

        JSONObject json = new JSONObject();

        if(cursor.moveToNext())
        {
            json.put("id",cursor.getInt(cursor.getColumnIndex("id")));
            json.put("name",cursor.getString(cursor.getColumnIndex("name")));
            json.put("sex",cursor.getInt(cursor.getColumnIndex("sex")));
            json.put("birthday",cursor.getString(cursor.getColumnIndex("birthday")));
            json.put("pswd",cursor.getString(cursor.getColumnIndex("pswd")));
            cursor.close();
        }
        return json;
    }
}