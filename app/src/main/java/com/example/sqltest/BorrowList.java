package com.example.sqltest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ListView;
import android.widget.Toast;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.test.JSONAssert;

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
        final MyDatabase mydb = new MyDatabase(this);
        final SQLiteDatabase db = mydb.getReadableDatabase();
        final String username = mydb.get_username(db).trim();
        final String pswd = mydb.get_pswd(db).trim();
        final DB_Demo db_demo =  new DB_Demo(this);
        final BorrowList t = this;
        final Object o = new Object();
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println("开始读取数据：BorrowList");
                if (db_demo.Get_user(username, pswd)) {
                    List<String> borrowlist = mydb.getBorrow(db);
                    List<JSONObject> borrow = db_demo.Get_table("param1=getBorrow&param2=" + username + "&param3=" + pswd);
                    System.out.println("获取到的数量：" + borrow.size() + "\n" + borrow);
                    for (int i = 1; i < borrowlist.size() + 1; i++) {
                        String booknum = borrowlist.get(i - 1);
                        System.out.println("获取图书id" + i + ": " + booknum);
                        if (!booknum.equals("0")) {
                            JSONObject item = db_demo.GetSearchBook("id", booknum).get(0);
                            for (int j = 0; j < borrow.size(); j++) {
                                System.out.println("开始检测..." + j + "");
                                if ((borrow.get(j).getInt("bookid") + "").equals(booknum)) {
                                    System.out.println("修改数据：" + booknum + "," + borrow.get(j).getInt("bookid"));
                                    item.put("publish", "归还期限：" + borrow.get(j).getString("returnday"));
                                    j = borrow.size();
                                } else {
                                    System.out.println("不匹配！");
                                }
                            }
                            list.add(item);
                        }
                    }
                } else {
                    try {
                        Toast.makeText(t, "请登录！", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Looper.prepare();
                        Toast.makeText(t, "请登录！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
                db.close();
                synchronized (o){
                    System.out.println("等待中...");
                    o.notify();
                }
            }
        };
        try{
            System.out.println("开始子线程...");
            thread.start();
            synchronized (o){
                o.wait(100);
            }
        }catch (Exception e){
            System.out.println("子线程错误！");
        }

        System.out.println("子线程执行中...");
        new Whale().DoWork(this,list,bookList);
    }
}