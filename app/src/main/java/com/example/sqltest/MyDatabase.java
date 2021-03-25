package com.example.sqltest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, "user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id integer,name varchar(20),sex int,birthday varchar(22),pswd varchar(40))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void add_data(SQLiteDatabase db,int id, String username, int sex, String birthday,String pswd){
        delete_all(db);
        System.out.println("开始为本地数据库添加数据...");
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("name", username);
        values.put("sex", sex);
        values.put("birthday",birthday );
        values.put("pswd",pswd);
        db.insert("user", null, values);
        db.close();
    }

    private void delete_all(SQLiteDatabase db) {
        db.delete("user",null,null);
//        db.close();
    }

    public void ____delete_all(SQLiteDatabase db){
        db.delete("user",null,null);
        db.close();
    }

    public void update(SQLiteDatabase db,int id, String username, int sex, String birthday,String pswd) {
            System.out.println("数据库中已有该数据");
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("name", username);
            values.put("sex", sex);
            values.put("birthday", birthday);
            values.put("pswd", pswd);
            db.update("user", values, "id = ?", new String[]{id + ""});
            db.close();
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

    public boolean checkUserById(SQLiteDatabase db,int id){
        String[] args  = new String[] {id+""};

        Cursor cursor=db.rawQuery("select * from user where id == ?",args);

        if(cursor.getCount()==0){
            return false;
        }
        return true;
    }
}