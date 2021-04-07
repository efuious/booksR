package com.example.sqltest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class BorrowList extends Activity {

    private List<JSONObject> list = new LinkedList<>();
    private ListView bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_list);

        init();
    }

    public void init(){
        System.out.println("我的借阅");
        bookList = findViewById(R.id.borrow_booklist);
        MyDatabase mydb = new MyDatabase(this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        String username = mydb.get_username(db);
        String pswd = mydb.get_pswd(db);
        DB_Demo db_demo =  new DB_Demo(this);
        if(db_demo.get_user(username,pswd)){
            List<String> borrowlist = mydb.getBorrow(db);
            for(int i=1;i<borrowlist.size()+1;i++){
                String booknum = borrowlist.get(i-1);
                System.out.println("获取图书id"+i+": "+booknum);
                if(!booknum.equals("0")) {
                    list.add(db_demo.getSearchBook("id", booknum).get(0));
                }
            }
        }else{
            Toast.makeText(this,"请登录！",Toast.LENGTH_SHORT).show();
        }
        db.close();
        new Whale().DoWork(this,list,bookList);
    }
}